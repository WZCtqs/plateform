package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.common.constant.Constants;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.delay.DelayMsgProvider;
import com.szhbl.framework.config.rabbit.delay.OrderDocDelayMsgProvider;
import com.szhbl.project.documentcenter.domain.DocDocumentsType;
import com.szhbl.project.documentcenter.domain.DocNewRemind;
import com.szhbl.project.documentcenter.domain.DocOrder;
import com.szhbl.project.documentcenter.domain.DocOrderSort;
import com.szhbl.project.documentcenter.domain.vo.DocOrderMsg;
import com.szhbl.project.documentcenter.domain.vo.DocRemindPage;
import com.szhbl.project.documentcenter.mapper.DocDocumentsTypeMapper;
import com.szhbl.project.documentcenter.mapper.DocNewRemindMapper;
import com.szhbl.project.documentcenter.mapper.DocOrderMapper;
import com.szhbl.project.documentcenter.service.IDocDocumentsTypeService;
import com.szhbl.project.documentcenter.service.IDocNewRemindService;
import com.szhbl.project.documentcenter.service.IDocOrderSortService;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.test.delayqueue.MessagePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 订单单证提醒设置Service业务层处理
 *
 * @author szhbl
 * @date 2020-03-30
 */
@Service
public class DocNewRemindServiceImpl implements IDocNewRemindService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DelayMsgProvider messageProvider;

    @Autowired
    private OrderDocDelayMsgProvider orderDocDelayMsgProvider;

    @Resource
    private DocNewRemindMapper docNewRemindMapper;

    @Resource
    private DocOrderMapper docOrderMapper;

    @Resource
    private DocDocumentsTypeMapper docDocumentsTypeMapper;

    @Autowired
    private IDocDocumentsTypeService documentsTypeService;

    @Autowired
    private IDocOrderSortService docOrderSortService;

    @Resource
    private BusiShippingorderMapper busiShippingorderMapper;

    /**
     * 查询订单单证提醒设置列表
     *
     * @param docRemindPage
     * @return
     */
    @Override
    public List<DocRemindPage> selectForRemindPage(DocRemindPage docRemindPage) {
        List<DocRemindPage> list;
        String deptCode = docRemindPage.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (docRemindPage.getDeptCode().contains("YWB")) {
                if (docRemindPage.getDeptCode().length() > 15) {
                    docRemindPage.setReadType("0");
                } else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    docRemindPage.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    docRemindPage.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    docRemindPage.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    docRemindPage.setReadType("5");
                } else {
                    docRemindPage.setReadType("1");
                }
            }
        }
        list = docNewRemindMapper.selectForRemindPage(docRemindPage);
        for (DocRemindPage page : list) {
            // 查询最新的节点维护提醒设置信息
            DocNewRemind param = new DocNewRemind();
            param.setFileTypeKey(page.getFileTypeKey());
            param.setOrderId(page.getOrderId());
            DocNewRemind newRemind = getDocNewRemind(param);
            if (newRemind != null) {
                page.setNormalSupplyNode(newRemind.getRemindTime());
            }
        }
        return list;
    }

    /**
     * 查询订单单证提醒设置列表
     *
     * @param docNewRemind 订单单证提醒设置
     * @return 订单单证提醒设置
     */
    @Override
    public List<DocNewRemind> selectDocNewRemindList(DocNewRemind docNewRemind) {
        return docNewRemindMapper.selectDocNewRemindList(docNewRemind);
    }

    /**
     * 新增订单单证提醒设置
     *
     * @param docNewRemind 订单单证提醒设置
     * @return 结果
     */
    @Override
    public int insertDocNewRemind(DocNewRemind docNewRemind) throws Exception {
        docNewRemind.setCreateTime(new Date());
        String typeKey = docNewRemind.getFileTypeKey();
        String orderId = docNewRemind.getOrderId();

        // 查询托书的信息，去回程，
        BusiShippingorders shippingorder = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId);
        docNewRemind.setExameTime(shippingorder.getExameTime());
        // 1. 托书和单证都存在，更改托书下该单证提醒时间
        if (StringUtils.isNotEmpty(typeKey) && StringUtils.isNotEmpty(orderId)) {
            DocDocumentsType docType = documentsTypeService.getTypeByTypeKey(typeKey);
            if (docType.getFileNotice() == null) {
                return 0;
            }
            sendByTypeKey(docType, docNewRemind);
        }
        // 2. 托书存在，单证不存在，更新托书下所有单证
        else if (StringUtils.isEmpty(typeKey) && StringUtils.isNotEmpty(orderId)) {
            sendByOrder(shippingorder, docNewRemind);
        }
        // 3. 托书不存在，单证存在，
        else if (StringUtils.isNotEmpty(typeKey) && StringUtils.isEmpty(orderId)) {
            DocDocumentsType docType = documentsTypeService.getTypeByTypeKey(typeKey);
            if (docType.getFileNotice() == null) {
                return 0;
            } else {
                docType.setFileNotice(docType.getFileNotice() + docNewRemind.getNewRemind());
                docDocumentsTypeMapper.updateDocDocumentsType(docType);
            }
        }
        // 4. 托书不存在，单证不存在，更改班列日期下所有托书的所有单证提醒时间
        else if (StringUtils.isNotEmpty(typeKey) && StringUtils.isEmpty(orderId)) {
            // 查询班列日期下所有已审核通过订单
            List<BusiShippingorders> ordersList = busiShippingorderMapper.selectBusiShippingorderByClassDate(
                    DateUtils.dateTime(docNewRemind.getClassDate()));
            for (BusiShippingorders order : ordersList) {
                sendByOrder(order, docNewRemind);
            }
        }
        return 1;
    }

    public void sendByOrder(BusiShippingorders order, DocNewRemind docNewRemind) throws Exception {
        List<DocDocumentsType> typeList = docDocumentsTypeMapper.selectByActiveArea(Integer.parseInt(order.getClassEastandwest()),
                Integer.parseInt(order.getIsconsolidation()));
        for (DocDocumentsType docType : typeList) {
            // 循环设置该托书下单证新提醒时间  (*单证存在默认提醒的时候)
            if (docType.getFileNotice() != null) {
                docNewRemind.setFileTypeKey(docType.getFileTypeKey());
                sendByTypeKey(docType, docNewRemind);
            }
        }
    }

    public void sendByTypeKey(DocDocumentsType docType, DocNewRemind docNewRemind) throws Exception {
        // 计算新的提醒日期时间
        Date remindTime = DateUtils.getPointTime(docNewRemind.getNewRemind() + docType.getFileNotice(),
                docType.getFileNoticeTime(), docNewRemind.getClassDate());
        docNewRemind.setRemindTime(remindTime);
        docNewRemind.setId(null);
        docNewRemindMapper.insertDocNewRemind(docNewRemind);
        // 更新托书单证理论提供时间
        DocOrder docOrder = new DocOrder();
        docOrder.setFileTypeKey(docType.getFileTypeKey());
        docOrder.setOrderId(docNewRemind.getOrderId());
        docOrder.setNormalSupplyNode(remindTime);
        docOrderMapper.updateByorderidTypeKey(docOrder);
        redisTemplate.opsForValue().set("docNewRemind:" + docNewRemind.getOrderId() + ':' + docNewRemind.getFileTypeKey(),
                docNewRemind);
        // -86400秒是因为需要提前一天进行提醒
        Long mill = DateUtils.getDatePoorLong(remindTime, new Date()) / 1000 - 86400L;
        // 如果时间差距离当前时间为负数，则设为 0
        mill = mill < 0 ? 0 : mill;
        /*次序保存*/
        DocOrderSort docOrderSort = new DocOrderSort();
        docOrderSort.setOrderId(docNewRemind.getOrderId());
        docOrderSort.setFileTypeKey(docNewRemind.getFileTypeKey());
        int sort = docOrderSortService.insertDocOrderSort(docOrderSort);
        // todo 暂时屏蔽客户之外的单证提醒
        if (docType.getFileFrom() == 1) {
            /*次序保存*/
            DocOrderMsg orderMsg = new DocOrderMsg();
            orderMsg.setSort(sort);
            orderMsg.setOrderId(docNewRemind.getOrderId());
            orderMsg.setClassDate(docNewRemind.getClassDate());
            orderMsg.setOrderNumber(docNewRemind.getOrderNumber());
            orderMsg.setFileTypeKey(docType.getFileTypeKey());
            orderMsg.setFileTypeText(docType.getFileTypeText());
            orderMsg.setFileTypeTextEn(docType.getFileTypeTextEn());
            orderMsg.setNewRemindId(docNewRemind.getId());
            orderMsg.setExameTime(docNewRemind.getExameTime());
            sendDelayMsg(orderMsg, mill);
        }
    }

    /**
     * 根据托书和单证类型查询最新设置
     *
     * @param docNewRemind
     * @return
     */
    @Override
    public DocNewRemind getDocNewRemind(DocNewRemind docNewRemind) {
        String key = "docNewRemind:" + docNewRemind.getOrderId() + ':' + docNewRemind.getFileTypeKey();
        DocNewRemind newRemind = (DocNewRemind) redisTemplate.opsForValue().get(key);
        if (newRemind == null) {
            newRemind = docNewRemindMapper.getDocNewRemind(docNewRemind);
            if (newRemind != null) {
                redisTemplate.opsForValue().set(key, newRemind);
            }
        }
        return newRemind;
    }

    /**
     * 逻辑删除新提醒设置
     *
     * @param docNewRemind
     * @return
     */
    @Override
    public int deleteDocNewRemindByOrder(DocNewRemind docNewRemind) {
        return docNewRemindMapper.deleteDocNewRemindByOrder(docNewRemind);
    }

    /**
     * 发送延迟消息
     *
     * @param msg   消息对象
     * @param delay 延迟时间
     * @throws Exception
     */
    public synchronized void sendDelayMsg(Object msg, Long delay) throws Exception {
        // 统计每天发送量并作为延迟秒数附加
//        String delayKey = Constants.RABBITMQ + ":" + DateUtils.dateTime();
//        redisTemplate.opsForValue().increment(delayKey);
        // 自增消息id
        String delayMessageKey = Constants.RABBITMQ + ":" + "delayMessageId";
        redisTemplate.opsForValue().increment(delayMessageKey);

        MessagePojo pojo = new MessagePojo();
        pojo.setMessage(msg);
        pojo.setMessageId(redisTemplate.opsForValue().get(delayMessageKey).toString());

        // 判断秒数是否大于 864,000‬ （10天秒数：10d*24h*60m*60s）
        if (delay > Constants.TENDAY_SECOND) {
            // 大于10天的话。延迟10天，并将延迟数 减10天记入消息
            pojo.setDelayNow(Constants.TENDAY_SECOND);
            pojo.setDelayNext(delay - Constants.TENDAY_SECOND);
        } else {
            pojo.setDelayNow(delay);
            pojo.setDelayNext(0L);
        }
        messageProvider.sendMessage(pojo);
    }
}
