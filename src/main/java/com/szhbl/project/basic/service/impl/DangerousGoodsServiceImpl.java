package com.szhbl.project.basic.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.project.basic.domain.DangerousGoods;
import com.szhbl.project.basic.mapper.DangerousGoodsMapper;
import com.szhbl.project.basic.service.IDangerousGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 危险品Service业务层处理
 *
 * @author dps
 * @date 2020-01-15
 */
@Service
public class DangerousGoodsServiceImpl implements IDangerousGoodsService {
    @Autowired
    private DangerousGoodsMapper dangerousGoodsMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询危险品
     *
     * @param goodsId 危险品ID
     * @return 危险品
     */
    @Override
    public DangerousGoods selectDangerousGoodsById(String goodsId) {
        return dangerousGoodsMapper.selectDangerousGoodsById(goodsId);
    }

    @Override
    public String getCnEnName(String name, String language) {
//        language = StringUtils.isChinese(name) ? "zh" : "en";
        String resultName = (String) redisTemplate.opsForValue().get("dangerous:" + language + ":" + name);
        if (resultName == null) {
            DangerousGoods dangerousGood = dangerousGoodsMapper.getCnEnName(name, language);
            if (dangerousGood != null) {
                if ("en".equals(language)) {
                    resultName = dangerousGood.getGoodsName();
                    redisTemplate.opsForValue().set("dangerous:" + language + ":" + name, resultName, 24, TimeUnit.HOURS);
                } else {
                    resultName = dangerousGood.getGoodsEnName();
                    redisTemplate.opsForValue().set("dangerous:" + language + ":" + name, resultName, 24, TimeUnit.HOURS);
                }
                return resultName;
            } else {
                return name;
            }
        } else {
            return resultName;
        }
    }

    /**
     * 查询危险品列表
     *
     * @param dangerousGoods 危险品
     * @return 危险品
     */
    @Override
    public List<DangerousGoods> selectDangerousGoodsList(DangerousGoods dangerousGoods) {
        return dangerousGoodsMapper.selectDangerousGoodsList(dangerousGoods);
    }

    /**
     * 新增危险品
     * 
     * @param dangerousGoods 危险品
     * @return 结果
     */
    @Override
    public int insertDangerousGoods(DangerousGoods dangerousGoods)
    {
        dangerousGoods.setCreatedate(DateUtils.getNowDate());
        dangerousGoods.setGoodsId(IdUtils.randomUUID());
        return dangerousGoodsMapper.insertDangerousGoods(dangerousGoods);
    }

    /**
     * 修改危险品
     * 
     * @param dangerousGoods 危险品
     * @return 结果
     */
    @Override
    public int updateDangerousGoods(DangerousGoods dangerousGoods)
    {
        dangerousGoods.setCreatedate(DateUtils.getNowDate());
        return dangerousGoodsMapper.updateDangerousGoods(dangerousGoods);
    }

    /**
     * 批量删除危险品
     * 
     * @param goodsIds 需要删除的危险品ID
     * @return 结果
     */
    @Override
    public int deleteDangerousGoodsByIds(String[] goodsIds)
    {
        return dangerousGoodsMapper.deleteDangerousGoodsByIds(goodsIds);
    }

    /**
     * 删除危险品信息
     * 
     * @param goodsId 危险品ID
     * @return 结果
     */
    @Override
    public int deleteDangerousGoodsById(String goodsId)
    {
        return dangerousGoodsMapper.deleteDangerousGoodsById(goodsId);
    }
}
