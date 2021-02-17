package com.szhbl.project.client.service.impl;

import java.util.List;

import com.szhbl.common.utils.KeyUtil;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.client.domain.BusiOperationquality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.client.mapper.BusiOperationqualityMapper;
import com.szhbl.project.client.service.IBusiOperationqualityService;

/**
 * 操作质量投诉Service业务层处理
 *
 * @author jhm
 * @date 2020-01-13
 */
@Service
public class BusiOperationqualityServiceImpl implements IBusiOperationqualityService
{
    @Autowired
    private BusiOperationqualityMapper busiOperationqualityMapper;

    /**
     * 查询操作质量投诉
     *
     * @param qualityId 操作质量投诉ID
     * @return 操作质量投诉
     */
    @Override
    public BusiOperationquality selectBusiOperationqualityById(String qualityId)
    {
        return busiOperationqualityMapper.selectBusiOperationqualityById(qualityId);
    }

    /**
     * 查询操作质量投诉列表
     *
     * @param busiOperationquality 操作质量投诉
     * @return 操作质量投诉
     */
    @Override
    public List<BusiOperationquality> selectBusiOperationqualityList(BusiOperationquality busiOperationquality)
    {
        String deptCode = busiOperationquality.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (busiOperationquality.getDeptCode().contains("YWB")) {
                if (busiOperationquality.getDeptCode().length() > 15) {
                    busiOperationquality.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    busiOperationquality.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    busiOperationquality.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    busiOperationquality.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    busiOperationquality.setReadType("5");
                } else {
                    busiOperationquality.setReadType("1");
                }
            }
        }
        return busiOperationqualityMapper.selectBusiOperationqualityList(busiOperationquality);
    }

    /**
     * 新增操作质量投诉
     *
     * @param busiOperationquality 操作质量投诉
     * @return 结果
     */
    @Override
    public int insertBusiOperationquality(BusiOperationquality busiOperationquality)
    {
        return busiOperationqualityMapper.insertBusiOperationquality(busiOperationquality);
    }

    /**
     * 修改操作质量投诉
     *
     * @param busiOperationquality 操作质量投诉
     * @return 结果
     */
    @Override
    public int updateBusiOperationquality(BusiOperationquality busiOperationquality)
    {
        //修改之前先查询表中是否存在order_id,如果存在就更新，否则新增
        List<BusiOperationquality> busiOperationqualityList =busiOperationqualityMapper.selectBusiOperationqualityWithOrderId(busiOperationquality.getOrderId());
        if(busiOperationqualityList.size()==0){
            //新增
            busiOperationquality.setQualityId( KeyUtil.genUniqueKey()) ;
            return  busiOperationqualityMapper.insertBusiOperationquality(busiOperationquality);
        }else{
            return busiOperationqualityMapper.updateBusiOperationquality(busiOperationquality);
        }

    }

    /**
     * 批量删除操作质量投诉
     *
     * @param qualityIds 需要删除的操作质量投诉ID
     * @return 结果
     */
    @Override
    public int deleteBusiOperationqualityByIds(String[] qualityIds)
    {
        return busiOperationqualityMapper.deleteBusiOperationqualityByIds(qualityIds);
    }

    /**
     * 删除操作质量投诉信息
     *
     * @param qualityId 操作质量投诉ID
     * @return 结果
     */
    @Override
    public int deleteBusiOperationqualityById(String qualityId)
    {
        return busiOperationqualityMapper.deleteBusiOperationqualityById(qualityId);
    }

//    @Override
//    public BusiOperationquality selectBusiOperationqualityWithOrderId(String orderId) {
//        return busiOperationqualityMapper.selectBusiOperationqualityWithOrderId(orderId);
//    }

    public BusiOperationqualityMapper getBusiOperationqualityMapper() {
        return busiOperationqualityMapper;
    }

    public void setBusiOperationqualityMapper(BusiOperationqualityMapper busiOperationqualityMapper) {
        this.busiOperationqualityMapper = busiOperationqualityMapper;
    }
}
