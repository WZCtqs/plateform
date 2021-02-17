package com.szhbl.project.trains.schedule;

import com.szhbl.project.trains.service.IBusiClassesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Component
public class CommonSchedule {

    @Autowired
    private IBusiClassesService busiClassesService;

    /**
     * 更新班列状态
     */
    //@Scheduled(cron="0 25 15 * * ?")
    @Scheduled(cron="0 1 0 * * ?")
    @GetMapping("/updateClassState")
    @ApiOperation("更新班列状态")
    public void updateClassState(){
        Date nowDate = new Date();
        busiClassesService.updateClassState(nowDate);
    }
}
