package com.szhbl.project.inquiry.service.impl;

import com.szhbl.common.enums.CountryEnum;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.inquiry.domain.JsBackTimeset;
import com.szhbl.project.inquiry.mapper.JsBackTimesetMapper;
import com.szhbl.project.inquiry.service.IJsBackTimesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 集疏部回程提货找车和运输时间Service业务层处理
 *
 * @author szhbl
 * @date 2020-07-21
 */
@Service
public class JsBackTimesetServiceImpl implements IJsBackTimesetService {
    @Autowired
    private JsBackTimesetMapper jsBackTimesetMapper;

    /**
     * 查询集疏部回程提货找车和运输时间
     *
     * @param id 集疏部回程提货找车和运输时间ID
     * @return 集疏部回程提货找车和运输时间
     */
    @Override
    public JsBackTimeset selectJsBackTimesetById(Long id) {
        return jsBackTimesetMapper.selectJsBackTimesetById(id);
    }

    /**
     * 查询集疏部回程提货找车和运输时间列表
     *
     * @param jsBackTimeset 集疏部回程提货找车和运输时间
     * @return 集疏部回程提货找车和运输时间
     */
    @Override
    public List<JsBackTimeset> selectJsBackTimesetList(JsBackTimeset jsBackTimeset) {
        return jsBackTimesetMapper.selectJsBackTimesetList(jsBackTimeset);
    }

    /**
     * 新增集疏部回程提货找车和运输时间
     *
     * @param jsBackTimeset 集疏部回程提货找车和运输时间
     * @return 结果
     */
    @Override
    public int insertJsBackTimeset(JsBackTimeset jsBackTimeset) {
        return jsBackTimesetMapper.insertJsBackTimeset(jsBackTimeset);
    }

    /**
     * 修改集疏部回程提货找车和运输时间
     *
     * @param jsBackTimeset 集疏部回程提货找车和运输时间
     * @return 结果
     */
    @Override
    public int updateJsBackTimeset(JsBackTimeset jsBackTimeset) {
        return jsBackTimesetMapper.updateJsBackTimeset(jsBackTimeset);
    }

    /**
     * 批量删除集疏部回程提货找车和运输时间
     *
     * @param ids 需要删除的集疏部回程提货找车和运输时间ID
     * @return 结果
     */
    @Override
    public int deleteJsBackTimesetByIds(Long[] ids) {
        return jsBackTimesetMapper.deleteJsBackTimesetByIds(ids);
    }

    /**
     * 删除集疏部回程提货找车和运输时间信息
     *
     * @param id 集疏部回程提货找车和运输时间ID
     * @return 结果
     */
    @Override
    public int deleteJsBackTimesetById(Long id) {
        return jsBackTimesetMapper.deleteJsBackTimesetById(id);
    }

    @Override
    public JsBackTimeset selectByStationPickCountry(String station, String pickcountry) {
        return jsBackTimesetMapper.selectByStationPickCountry(station, pickcountry);
    }

    @Override
    public AjaxResult getCountry(String language) {
        CountryEnum countrys[] = CountryEnum.values();
        if ("en".equals(language)) {
            String countrysEn[] = new String[countrys.length];
            int i = 0;
            for (CountryEnum c : countrys) {
                countrysEn[i] = c.enName;
                i++;
            }
            return AjaxResult.success(countrysEn);
        } else {
            return AjaxResult.success(countrys);
        }
    }
}
