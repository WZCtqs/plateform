package com.szhbl.project.monitor.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.domain.Server;

/**
 * 服务器监控
 * 
 * @author szhbl
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController extends BaseController
{
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
