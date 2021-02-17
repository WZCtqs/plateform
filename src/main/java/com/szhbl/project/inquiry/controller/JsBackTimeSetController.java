package com.szhbl.project.inquiry.controller;

import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.inquiry.service.IJsBackTimesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description : 集输回程找车运输时间节点设置
 * @Author : wangzhichao
 * @Date: 2020-08-17 08:22
 */
@RestController
@RequestMapping("/jsbacktime")
public class JsBackTimeSetController extends BaseController {

    @Autowired
    private IJsBackTimesetService jsBackTimesetService;


    @GetMapping("/countrylist")
    public AjaxResult getCountry(String language) {
        return jsBackTimesetService.getCountry(language);
    }

}
