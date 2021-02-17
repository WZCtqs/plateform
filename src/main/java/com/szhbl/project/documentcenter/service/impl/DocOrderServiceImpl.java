package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.common.constant.Constants;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.delay.DelayMsgProvider;
import com.szhbl.framework.config.rabbit.delay.OrderDocDelayMsgProvider;
import com.szhbl.project.documentcenter.domain.DocDocumentsType;
import com.szhbl.project.documentcenter.domain.DocOrder;
import com.szhbl.project.documentcenter.domain.DocOrderSort;
import com.szhbl.project.documentcenter.domain.vo.DocOrderMsg;
import com.szhbl.project.documentcenter.mapper.DocDocumentsTypeMapper;
import com.szhbl.project.documentcenter.mapper.DocOrderMapper;
import com.szhbl.project.documentcenter.service.IDocOrderService;
import com.szhbl.project.documentcenter.service.IDocOrderSortService;
import com.szhbl.project.monitor.mapper.SysMessageMapper;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.test.delayqueue.MessagePojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单—单证（订单所需单证）Service业务层处理
 *
 * @author hp
 * @date 2020-04-13
 */
@Slf4j
@Service
public class DocOrderServiceImpl implements IDocOrderService
{
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DelayMsgProvider messageProvider;

    @Autowired
    private OrderDocDelayMsgProvider orderDocDelayMsgProvider;

    @Resource
    private DocOrderMapper docOrderMapper;

    @Resource
    private DocDocumentsTypeMapper docDocumentsTypeMapper;

    @Resource
    private BusiShippingorderMapper busiShippingorderMapper;

    @Autowired
    private IDocOrderSortService docOrderSortService;

    @Autowired
    private SysMessageMapper sysMessageMapper;

    /**
     * 查询订单—单证（订单所需单证）
     *
     * @param orderId 订单—单证（订单所需单证）ID
     * @return 订单—单证（订单所需单证）
     */
    @Override
    public List<DocOrder> selectDocOrderByOrderId(String orderId) {
        return docOrderMapper.selectDocOrderByOrderId(orderId);
    }

    @Override
    public DocOrder selectByOrderidTypeKey(String orderId, String fileTypeKey) {
        return docOrderMapper.selectByOrderidTypeKey(orderId, fileTypeKey);
    }

    @Override
    public int updateByorderidTypeKey(DocOrder docOrder){
        return docOrderMapper.updateByorderidTypeKey(docOrder);
    }

    @Override
    public int updateActualSupply(DocOrder docOrder){
        return docOrderMapper.updateActualSupply(docOrder);
    }


    /**
     * 查询订单—单证（订单所需单证）列表
     *
     * @param docOrder 订单—单证（订单所需单证）
     * @return 订单—单证（订单所需单证）
     */
    @Override
    public List<DocOrder> selectDocOrderList(DocOrder docOrder) {
        String deptCode = docOrder.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (docOrder.getDeptCode().contains("YWB")) {
                if (docOrder.getDeptCode().length() > 15) {
                    docOrder.setReadType("0");
                } else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    docOrder.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    docOrder.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    docOrder.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    docOrder.setReadType("5");
                } else {
                    docOrder.setReadType("1");
                }
            }
        }
        return docOrderMapper.selectDocOrderList(docOrder);
    }

    /**
     * 新增订单—单证（订单所需单证）
     *
     * @param docOrder 订单—单证（订单所需单证）
     * @return 结果
     */
    @Override
    public int insertDocOrder(DocOrder docOrder)
    {
        return docOrderMapper.insertDocOrder(docOrder);
    }

    @Override
    public int insertDocOrderMatch(String orderId) throws Exception {
        /*删除原托书单证表信息*/
        docOrderMapper.deleteDocOrderByOrderId(orderId);
        // 获取托书信息
        BusiShippingorders order = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId);
        /*删除消息中原托书编号消息*/
        String oldOrderNumber = order.getOrderNumber().trim();
        if (oldOrderNumber.contains("-")) {
            oldOrderNumber = oldOrderNumber.substring(0, oldOrderNumber.indexOf("-")).trim();
        }
        sysMessageMapper.deleteFileMsgLikeOrderNumber(oldOrderNumber.trim());
        // 获取托书所需单证信息
        List<DocDocumentsType> typeList = docDocumentsTypeMapper.selectByActiveArea(Integer.parseInt(order.getClassEastandwest()),
                Integer.parseInt(order.getIsconsolidation()));
        List<DocOrder> docOrderList = new ArrayList<>();
        for (DocDocumentsType docType : typeList) {
            DocOrder docOrder = new DocOrder();
            docOrder.setOrderId(orderId);
            docOrder.setClassDate(order.getClassDate());
            docOrder.setAuditTime(order.getExameTime());
            docOrder.setOrderNumber(order.getOrderNumber().trim());
            docOrder.setFileTypeKey(docType.getFileTypeKey());
            docOrder.setFileTypeText(docType.getFileTypeText());
            // 存在单证提醒
            // todo 暂时屏蔽客户之前的单证提醒
            if (docType.getFileNotice() != null && docType.getFileFrom() == 1) {
                // 计算理论提供时间节点
                Date date = DateUtils.getPointTime(docType.getFileNotice(), docType.getFileNoticeTime(), order.getClassDate());
                docOrder.setNormalSupplyNode(date);
                /*次序保存*/
                DocOrderSort docOrderSort = new DocOrderSort();
                docOrderSort.setOrderId(order.getOrderId());
                docOrderSort.setFileTypeKey(docType.getFileTypeKey());
                int sort = docOrderSortService.insertDocOrderSort(docOrderSort);
                /*次序保存*/
                // 消息发送
                MessagePojo pojo = new MessagePojo();
                DocOrderMsg msg = new DocOrderMsg();
                msg.setSort(sort);
                msg.setOrderId(orderId);
                msg.setClassDate(order.getClassDate());
                msg.setFileTypeKey(docType.getFileTypeKey());
                msg.setFileTypeText(docType.getFileTypeText());
                msg.setFileTypeTextEn(docType.getFileTypeTextEn());
                msg.setOrderNumber(order.getOrderNumber());
                msg.setExameTime(order.getExameTime());
                log.info("发送消息：" + msg);
                pojo.setMessage(msg);
                // -86400秒是因为需要提前一天进行提醒
                Long mill = DateUtils.getDatePoorLong(date, new Date())/1000 - 86400L;
                // 如果时间差距离当前时间为负数，则设为 0
                mill = mill < 0 ? 0 : mill;
                sendDelayMsg(msg, mill);
            }
            docOrderList.add(docOrder);
        }
        return docOrderMapper.insertDocOrderMatch(docOrderList);
    }

    /**
     * 删除订单—单证（订单所需单证）信息
     *
     * @param orderId 订单—单证（订单所需单证）ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderByOrderId(String orderId)
    {
        return docOrderMapper.deleteDocOrderByOrderId(orderId);
    }

    /**
     * 发送延迟消息
     * @param msg 消息对象
     * @param delay 延迟时间
     * @throws Exception
     */
    public synchronized void sendDelayMsg(DocOrderMsg msg, Long delay) throws Exception {
        // 统计每天发送量并作为延迟秒数附加
//        String delayKey = Constants.RABBITMQ + ":" + DateUtils.dateTime();
//        redisTemplate.opsForValue().increment(delayKey);
        // 自增消息id
        String delayMessageKey =  Constants.RABBITMQ + ":" + "delayMessageId";
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
