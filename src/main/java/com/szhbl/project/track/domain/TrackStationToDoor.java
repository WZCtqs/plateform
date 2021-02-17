package com.szhbl.project.track.domain;


import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 站到门/门到站 track_station_to_door
 *
 * @author szhbl
 *
 */
@Data
public class TrackStationToDoor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    //id
    private Long id;

    //订单id order_id
    private String orderId;

    //订舱草稿时间 draft_time
    private String draftTime;

    //取消托书时间 cancel_book_time
    private String cancelBookTime;

    //待审核时间 check_pending_time
    private String checkPendingTime;

    //审核结果（通过/失败）时间 check_result_time
    private String checkResultTime;

    //审核结果0通过1失败 check_result
    private Integer checkResult;

    //审核失败原因 check_fail_reason
    private String checkFailReason;

    //转待审核时间 check_again_time
    private String checkAgainTime;

    //转待审核结果（通过/失败）时间 check_again_result_time
    private String checkAgainResultTime;

    //转待审核结果0通过1失败 check_again_result
    private Integer checkAgainResult;

    //转待审核失败原因 check_again_fail_reason
    private String checkAgainFailReason;

    //放箱时间 release_box_time
    private String releaseBoxTime;

    //派车时间 send_car_time
    private String sendCarTime;

    //司机信息 driver_information
    private String driverInformation;

    //提箱时间(门到站) carry_box_time
    private String carryBoxTime;

    //提货时间 carry_goods_time
    private String carryGoodsTime;

    //预计送达时间（门到站，运输中） expect_arrive_time
    private String expectArriveTime;

    //送达时间(门到站) arrive_time
    private String arriveTime;

    //入仓时间 in_store_time
    private String inStoreTime;

    //进站时间 in_station_time
    private String inStationTime;

    //单据提供时间（/报关|清关） bills_provide_time
    private String billsProvideTime;

    //问题沟通时间（沟通中） problem_communicate_time
    private String problemCommunicateTime;

    //草单确认时间 straw_sure_time
    private String strawSureTime;

    //正本提供时间 original_provide_time
    private String originalProvideTime;

    //申报时间 apply_time
    private String applyTime;

    //缴税时间（回程） pay_tax_time
    private String payTaxTime;

    //布控时间 layout_time
    private String layoutTime;

    //布控原因 layout_reason
    private String layoutReason;

    //重量异常（回程） weight_abnormal
    private String weightAbnormal;

    //查验时间（回程） inspection_time
    private String inspectionTime;

    //查验原因（回程） inspection_reason
    private String inspectionReason;

    //放行时间（报关|清关/） release_time
    private String releaseTime;

    //单证提供时间(回程) document_provide_time
    private String documentProvideTime;

    //单证审核结果0通过1失败（回程） document_check_result
    private Integer documentCheckResult;

    //单证审核时间（回程） document_check_time
    private String documentCheckTime;

    //班列发车时间 train_depart_time
    private String trainDepartTime;

    //提箱资料发送时间（/整柜） carry_box_information_time
    private String carryBoxInformationTime;

    //提箱时间（站到门） carry_container_time
    private String carryContainerTime;

    //司机信息（回程） driver_news
    private String driverNews;

    //预计送达时间（站到门） expect_reach_time
    private String expectReachTime;

    //送达时间（站到门） reach_time
    private String reachTime;

    //签收时间 sign_time
    private String signTime;

    //还箱时间（整柜/) return_box_time
    private String returnBoxTime;

    //提货时间（回程拼箱中心站到堆场） get_cargo_time
    private String getCargoTime;

    //拆箱完成时间（/拼箱） devanning_time
    private String devanningTime;

    //预计可提货时间 expect_carry_cargo_time
    private String expectCarryCargoTime;

    //提货时间 carry_cargo_time
    private String carryCargoTime;

    //司机信息 driver_message
    private String driverMessage;

    //预计送达时间 except_come_time
    private String exceptComeTime;

    //送达时间 come_time
    private String comeTime;

    //签收时间（拼箱/） receive_time
    private String receiveTime;

    //删除标志,0存在，1删除
    private int  delFlag;

    @Override
    public String toString() {
        return "TrackStationToDoor{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", draftTime='" + draftTime + '\'' +
                ", cancelBookTime='" + cancelBookTime + '\'' +
                ", checkPendingTime='" + checkPendingTime + '\'' +
                ", checkResultTime='" + checkResultTime + '\'' +
                ", checkResult=" + checkResult +
                ", checkFailReason='" + checkFailReason + '\'' +
                ", checkAgainTime='" + checkAgainTime + '\'' +
                ", checkAgainResultTime='" + checkAgainResultTime + '\'' +
                ", checkAgainResult=" + checkAgainResult +
                ", checkAgainFailReason='" + checkAgainFailReason + '\'' +
                ", releaseBoxTime='" + releaseBoxTime + '\'' +
                ", sendCarTime='" + sendCarTime + '\'' +
                ", driverInformation='" + driverInformation + '\'' +
                ", carryBoxTime='" + carryBoxTime + '\'' +
                ", carryGoodsTime='" + carryGoodsTime + '\'' +
                ", expectArriveTime='" + expectArriveTime + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                ", inStoreTime='" + inStoreTime + '\'' +
                ", inStationTime='" + inStationTime + '\'' +
                ", billsProvideTime='" + billsProvideTime + '\'' +
                ", problemCommunicateTime='" + problemCommunicateTime + '\'' +
                ", strawSureTime='" + strawSureTime + '\'' +
                ", originalProvideTime='" + originalProvideTime + '\'' +
                ", applyTime='" + applyTime + '\'' +
                ", payTaxTime='" + payTaxTime + '\'' +
                ", layoutTime='" + layoutTime + '\'' +
                ", layoutReason='" + layoutReason + '\'' +
                ", weightAbnormal='" + weightAbnormal + '\'' +
                ", inspectionTime='" + inspectionTime + '\'' +
                ", inspectionReason='" + inspectionReason + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", documentProvideTime='" + documentProvideTime + '\'' +
                ", documentCheckResult=" + documentCheckResult +
                ", documentCheckTime='" + documentCheckTime + '\'' +
                ", trainDepartTime='" + trainDepartTime + '\'' +
                ", carryBoxInformationTime='" + carryBoxInformationTime + '\'' +
                ", carryContainerTime='" + carryContainerTime + '\'' +
                ", driverNews='" + driverNews + '\'' +
                ", expectReachTime='" + expectReachTime + '\'' +
                ", reachTime='" + reachTime + '\'' +
                ", signTime='" + signTime + '\'' +
                ", returnBoxTime='" + returnBoxTime + '\'' +
                ", getCargoTime='" + getCargoTime + '\'' +
                ", devanningTime='" + devanningTime + '\'' +
                ", expectCarryCargoTime='" + expectCarryCargoTime + '\'' +
                ", carryCargoTime='" + carryCargoTime + '\'' +
                ", driverMessage='" + driverMessage + '\'' +
                ", exceptComeTime='" + exceptComeTime + '\'' +
                ", comeTime='" + comeTime + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}
