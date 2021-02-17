package com.szhbl.project.order.schedule;

import com.szhbl.project.order.service.IBusiShippingorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CommonScheduleOrder {

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    /**
     * 更新转待审核次数
     */
    @Scheduled(cron="0 0 0 * * ?")
    public void updateTurnCount(){
        busiShippingorderService.updateTurnCount();
    }
}
