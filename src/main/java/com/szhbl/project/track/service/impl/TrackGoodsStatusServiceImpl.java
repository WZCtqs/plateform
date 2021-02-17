package com.szhbl.project.track.service.impl;

import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.IBusiZyInfoService;
import com.szhbl.project.system.domain.SysUser;
import com.szhbl.project.system.mapper.SysUserMapper;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.domain.vo.OrderGoodsVo;
import com.szhbl.project.track.mapper.TrackGoodsStatusMapper;
import com.szhbl.project.track.service.ITrackGoodsStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 货物状态表Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-08
 */
@Service
public class TrackGoodsStatusServiceImpl implements ITrackGoodsStatusService
{
    @Autowired
    private TrackGoodsStatusMapper trackGoodsStatusMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private IBusiZyInfoService busiZyInfoService;
  /**
     * 货物状态表
     * 
     * @param file 导入文件
     * @return
     */
    @Override
    public AjaxResult importData(MultipartFile file)  throws Exception {
        ExcelUtil<TrackGoodsStatus> excelUtil = new ExcelUtil<>(TrackGoodsStatus.class);
        List<TrackGoodsStatus> tgsList = excelUtil.importExcel(file.getInputStream());
        List<TrackGoodsStatus> list=new ArrayList<>();
        for(int i=0;i<tgsList.size();i++){
            if(StringUtils.isNotEmpty(tgsList.get(i).getOrderNum())){
                list.add(tgsList.get(i));
            }
        }
        int total=list.size();
        int success=0;
        List<String> orderNum=new ArrayList<>();
        TrackGoodsStatus tgs=null;
        BusiZyInfo zyinfo = null;
        TrackGoodsStatus trackGoodsStatus= null;
        for(int i=0;i<total;i++){//舱位号，箱号，实际班列日期
            TrackGoodsStatus Tgs=list.get(i);
            tgs=new TrackGoodsStatus();
            tgs.setBoxNum(Tgs.getBoxNum());
            tgs.setOrderNum(Tgs.getOrderNum());
            List<TrackGoodsStatus> checkTgsList=trackGoodsStatusMapper.selectGoodsStatusList(tgs);//根据舱位号和箱号查询货物状态是否存在
            if(checkTgsList.size()==1){
                Tgs.setId(checkTgsList.get(0).getId());
                int update= updateTgs(Tgs);
                if(update==0){
                    orderNum.add(Tgs.getOrderNum());
                }
                success+=update;
            }else{
                Tgs.setCreateTime(new Date());
                Tgs.setUpdateTime(new Date());
                int insert= trackGoodsStatusMapper.insertTgs(Tgs);
                if(insert==0){
                    orderNum.add(Tgs.getOrderNum());
                }else if(insert==1){
                    zyinfo = new BusiZyInfo();
                    trackGoodsStatus=trackGoodsStatusMapper.selectById(Tgs.getId());
                    zyinfo.setTrackId(trackGoodsStatus.getId()); //货物状态表id
                    zyinfo.setOrderId(trackGoodsStatus.getOrderId()); //订单id
                    zyinfo.setOrderNumber(Tgs.getOrderNum()); //订单编号
                    zyinfo.setXianghao(trackGoodsStatus.getBoxNum());//箱号
                    busiZyInfoService.insertBusiZyInfo(zyinfo);
                }
                success+=insert;
            }
        }
        if(total==success){
            if(total==0){
                return AjaxResult.error("无数据可导入，请重新选择文件");
            }else{
                return AjaxResult.success("成功导入"+total+"条数据");
            }
        }else{
            return AjaxResult.error("导入"+(total-success)+"条数据失败,舱位号分别为"+StringUtils.join(orderNum,",")+"请先确认该舱位号是否存在");
        }
    }

    /**
     * 货物状态表
     *
     * @param Tgs 查询条件
     * @return
     */
    @Override
    public List<TrackGoodsStatus> selectGoodsStatusList(TrackGoodsStatus Tgs) {
        List<TrackGoodsStatus> tgsList=trackGoodsStatusMapper.selectGoodsStatusList(Tgs);
        for(int i=0;i<tgsList.size();i++){
            String eastAndWest=tgsList.get(i).getEastAndWest();
            String site=null;
            String merchandiser="";
            String merchandiserId=null;
            if("0".equals(eastAndWest)){//0去程西向跟单员  去程提取下货站
                merchandiserId=tgsList.get(i).getWMerchandiserId();
                site=tgsList.get(i).getUnloadSite();
            }else if("1".equals(eastAndWest)){//1回程东向跟单员  回程提取上货站
                merchandiserId=tgsList.get(i).getEMerchandiserId();
                site=tgsList.get(i).getUploadSite();
            }
            if(StringUtils.isNotEmpty(merchandiserId)){
                String[] mcsIds=merchandiserId.replaceAll(" ",",").replaceAll("，",",").replaceAll("\\|",",").split(",");
                for(int j=0;j<mcsIds.length;j++){
                   // SysUser sysUser=sysUserMapper.selectUserById(Long.valueOf(mcsIds[j]));
                    SysUser sysUser=sysUserMapper.selectUserByDcId(mcsIds[j]);
                    if(sysUser!=null){
                        if("".equals(merchandiser)){
                            merchandiser=sysUser.getNickName();
                        }else{
                            merchandiser=merchandiser+","+sysUser.getNickName();
                        }
                    }
                }
            }
            tgsList.get(i).setMerchandiser(merchandiser);
            tgsList.get(i).setSite(site);
        }
        return tgsList;
    }

    /**
     * 根据id进行货物状态表数据查询
     *
     * @param id 查询条件
     * @return
     */
    @Override
    public TrackGoodsStatus selectById(Long id) {
        return trackGoodsStatusMapper.selectById(id);
    }

    /**
     * 货物状态表修改
     *
     * @param Tgs 查询条件
     * @return
     */
    @Override
    public int updateTgs(TrackGoodsStatus Tgs) {
        //Tgs.setActualClassDate(getActualDate(Tgs.getOrderId()));
        TrackGoodsStatus checkTgs=trackGoodsStatusMapper.selectById(Tgs.getId());
        ShippingOrder shippingOrder=busiShippingorderMapper.selectBusiShippingorderById(checkTgs.getOrderId());
        if(shippingOrder!=null&&!"2".equals(shippingOrder.getLineTypeid())
                &&!StringUtils.equals(shippingOrder.getClassId(),checkTgs.getActualClassId())){
            Tgs.setActualClassId(shippingOrder.getClassId());
        }
        //第一次录入异常箱数据
        if(Tgs.getIsNormal()!=null&&checkTgs.getIsNormal()==0&&Tgs.getIsNormal()==1){
            Tgs.setAbnormalTime(new Date());
        }
        //实际班列日期为空或者不为空但不含X,第一次录入带X的运踪
        if(StringUtils.isNotEmpty(Tgs.getActualClassDate())){
            if(StringUtils.isNotEmpty(checkTgs.getActualClassDate())&&(!checkTgs.getActualClassDate().contains("X")&&!checkTgs.getActualClassDate().contains("x"))
                    &&(Tgs.getActualClassDate().contains("X")||Tgs.getActualClassDate().contains("x"))){
                Tgs.setBatchTime(new Date());
                Tgs.setBatchDate(Tgs.getActualClassDate());
            }
            if(StringUtils.isEmpty(checkTgs.getActualClassDate())&&(Tgs.getActualClassDate().contains("X")||Tgs.getActualClassDate().contains("x"))){
                Tgs.setBatchTime(new Date());
                Tgs.setBatchDate(Tgs.getActualClassDate());
            }
            //第2,3,4次录入
            if((Tgs.getActualClassDate().contains("X")||Tgs.getActualClassDate().contains("x"))
                    &&(checkTgs.getActualClassDate().contains("X")||checkTgs.getActualClassDate().contains("x"))&&(!checkTgs.getActualClassDate().equals(Tgs.getActualClassDate()))){
                if(StringUtils.isEmpty(checkTgs.getBatchDate2())){
                    Tgs.setBatchTime2(new Date());
                    Tgs.setBatchDate2(Tgs.getActualClassDate());
                }else if(StringUtils.isEmpty(Tgs.getBatchDate3())){
                    Tgs.setBatchTime3(new Date());
                    Tgs.setBatchDate3(Tgs.getActualClassDate());
                }else if(StringUtils.isEmpty(checkTgs.getBatchDate4())){
                    Tgs.setBatchTime4(new Date());
                    Tgs.setBatchDate4(Tgs.getActualClassDate());
                }
            }
        }
        Tgs.setUpdateTime(new Date());
        if(Tgs.getIsNormal()!=null&&Tgs.getIsNormal()==1&&!Tgs.getActualClassDate().endsWith("D")&&!Tgs.getActualClassDate().endsWith("-")){
            Tgs.setActualClassDate(Tgs.getActualClassDate()+"-D");
        }
        return trackGoodsStatusMapper.updateTgs(Tgs);
    }

    /**
     * 货物品名查询
     *
     * @param ogv 查询条件
     * @return AjaxResult
     *
     */
    @Override
    public List<OrderGoodsVo> selectGoodsList(OrderGoodsVo ogv) {
        //去程就是国内报关HS，国外清关HS
        //回程就是国内清关HS，国外报关HS
        //    /** 国内清关HS */
        //    private String goodsClearance;
        //    /** 国外清关HS */
        //    private String goodsOutClearance;
        //    /** 国内报关HS */
        //    private String goodsInReport;
        //    /** 国外报关HS */
        //    private String goodsReport;
      /*  List<OrderGoodsVo> ogvList=trackGoodsStatusMapper.selectGoodsList(ogv);
        for(int i=0;i<ogvList.size();i++){
           String classEastAndWest=ogvList.get(i).getClassEastAndWest();
           if("0".equals(classEastAndWest)){//0为去(西向) 1为回(东向）
               ogvList.get(i).setInCode(ogvList.get(i).getGoodsInReport());
               ogvList.get(i).setOutCode(ogvList.get(i).getGoodsOutClearance());
           }else if("1".equals(classEastAndWest)){
               ogvList.get(i).setInCode(ogvList.get(i).getGoodsClearance());
               ogvList.get(i).setOutCode(ogvList.get(i).getGoodsReport());
           }
        }
        return ogvList;*/
        return trackGoodsStatusMapper.selectGoodsList(ogv);
    }

    //根据订单id和箱号查看数据库是否有该条数据
    @Override
    public TrackGoodsStatus checkTgs(TrackGoodsStatus trackGoodsStatus){
        return trackGoodsStatusMapper.checkTgs(trackGoodsStatus);
    }

    //新增箱舱匹配数据
    @Override
    public int insertXcppTgs(TrackGoodsStatus trackGoodsStatus){
        //trackGoodsStatus.setActualClassDate(getActualDate(trackGoodsStatus.getOrderId()));
        trackGoodsStatus.setCreateTime(new Date());
        /*ShippingOrder shippingOrder=busiShippingorderMapper.selectBusiShippingorderById(trackGoodsStatus.getOrderId());
        TrackGoodsStatus Tgs=new TrackGoodsStatus();
        Tgs.setOrderId(trackGoodsStatus.getOrderId());
        List<TrackGoodsStatus> tgsList=trackGoodsStatusMapper.selectGoodsStatusList(Tgs);
        货物状态表数据和箱量对比
        if("0".equals(shippingOrder.getIsconsolidation())&&tgsList.size()>=Integer.valueOf(shippingOrder.getContainerBoxamount())){
            //更新
            tgsList.get(0).setDelFlag(1);
            trackGoodsStatusMapper.updateTgs(tgsList.get(0));
            busiZyInfoService.deleteZyInfoByTrack(map.get("order_id"),map.get("xianghao"));

        }*/
        return trackGoodsStatusMapper.insertXcppTgs(trackGoodsStatus);
    }

    //删除箱舱匹配数据
    @Override
    public int deleteXcppTgs(TrackGoodsStatus trackGoodsStatus){
        return trackGoodsStatusMapper.updateTgs(trackGoodsStatus);
    }

    //增加箱舱匹配数据
    @Override
    public int addTgs(TrackGoodsStatus trackGoodsStatus){
        return trackGoodsStatusMapper.addTgs(trackGoodsStatus);
    }

    //根据班列日期获取实际班列日期
    public String getActualDate(String orderId){
        //根据orderid获取班列编号
        String actualDate =null;
        ShippingOrder shippingOrder=busiShippingorderMapper.selectBusiShippingorderById(orderId);
        if(shippingOrder!=null){
            String classBh=shippingOrder.getClassBh();
            if(StringUtils.isNotEmpty(classBh)) {
                for (int i = 0; i < classBh.length(); i++) {
                    if (classBh.charAt(i) >= 48 && classBh.charAt(i) <= 57) {
                        actualDate = classBh.substring(i+2);
                        break;
                    }
                }
            }
            if(StringUtils.isNotEmpty(actualDate)){
                if(actualDate.startsWith("0")){
                    actualDate=actualDate.substring(1,2)+"."+actualDate.substring(2);
                }else{
                    actualDate=actualDate.substring(0,2)+"."+actualDate.substring(2);
                }
            }
        }
        return actualDate;
    }
}
