package com.szhbl.project.track.domain.cencus;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

//运行时间统计去程二连浩特导出vo
@Data
public class GoErenhotCensus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 班列id */
    private String classId;

    /** 是否已编辑null未编辑1已编辑 */
    private Integer isEdit;

    /** 换装日期 */
    private String changeDate;

    /** 班列日期*/
    @Excel(name="班列日期")
    private String classDate;

    /** 出入境口岸*/
    @Excel(name="出境口岸")
    private String port;

    /** 换装地 */
    @Excel(name="换装地")
    private String changePlace;

    /** 图定发车时间 */
    @Excel(name="图定发车时间")
    private String planRunTime;

    /** 实际发车时间 */
    @Excel(name="实际发车时间")
    private String actualRunTime;

    /** 国内运行天数/始发站到第一个站点天数 */
    @Excel(name="国内段运行时间")
    private String domesticDay;

    /** 到达第一个地点时间 */
    @Excel(name="到达二连")
    private String arriveOneTime;

    /** 第一个地点停留天数 */
    @Excel(name="停留时间")
    private String oneStayDay;

    /** 离开第一个地点时间 */
    @Excel(name="驶离二连")
    private String departOneTime;

    /** 第一个地点到第二个地点天数 */
    @Excel(name="二连—扎门乌德")
    private String oneTwoDay;

    /** 到达第二个地点时间 */
    @Excel(name="到达扎门乌德")
    private String arriveTwoTime;

    /** 第二个地点停留天数 */
    @Excel(name="停留时间")
    private String twoStayDay;

    /** 离开第二个地点时间 */
    @Excel(name="驶离扎门乌德")
    private String departTwoTime;

    /** 第二个地点到第三个地点天数 */
    @Excel(name="扎门乌德—布列斯特")
    private String twoThrDay;

    /** 到达第三个地点时间 */
    @Excel(name="到达布列斯特")
    private String arriveThrTime;

    /** 第三个地点停留天数 */
    @Excel(name="停留时间")
    private String thrStayDay;

    /** 离开第三个地点时间 */
    @Excel(name="驶离布列斯特")
    private String departThrTime;

    /** 第三个地点到第四个地点天数 */
    @Excel(name="布列斯特-马拉")
    private String thrFouDay;

    /** 到达第四个地点时间 */
    @Excel(name="到达马拉")
    private String arriveFouTime;

    /** 第四个地点停留天数 */
    @Excel(name="停留时间")
    private String fouStayDay;

    /** 离开第四个地点时间 */
    @Excel(name="驶离马拉")
    private String departFouTime;

    /** 最后一个地点到目的地天数 */
    @Excel(name="马拉-到目的站")
    private String lastDestinationDate;

    /** 到达目的地时间 */
    @Excel(name="目的站")
    private String destinationTime;

    /** 境内运行天数 */
    @Excel(name="境内运行时间")
    private String territoryDay;

    /** 全程运行天数 */
    @Excel(name="全程运行时间")
    private String totalDay;

    /** 延迟原因 */
    @Excel(name="延迟原因")
    private String delayReason;

    /** 往返，去0 回1*/
    private String classEastAndWest;

    /**开行班列*/
    private String classBlockTrain;

    /** 目的站名称 */
    private String classStationofdestinationName;

    /*** 班列日期 开始*/
    private String startTime;

    /*** 班列日期 结束*/
    private String endTime;


    /** 删除标志0正常1删除 */
    private Integer delFlag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("classId", getClassId())
            .append("planRunTime", getPlanRunTime())
            .append("actualRunTime", getActualRunTime())
            .append("domesticDay", getDomesticDay())
            .append("arriveOneTime", getArriveOneTime())
            .append("oneStayDay", getOneStayDay())
            .append("departOneTime", getDepartOneTime())
            .append("oneTwoDay", getOneTwoDay())
            .append("arriveTwoTime", getArriveTwoTime())
            .append("twoStayDay", getTwoStayDay())
            .append("departTwoTime", getDepartTwoTime())
            .append("twoThrDay", getTwoThrDay())
            .append("arriveThrTime", getArriveThrTime())
            .append("thrStayDay", getThrStayDay())
            .append("departThrTime", getDepartThrTime())
            .append("thrFouDay", getThrFouDay())
            .append("arriveFouTime", getArriveFouTime())
            .append("fouStayDay", getFouStayDay())
            .append("departFouTime", getDepartFouTime())
            .append("lastDestinationDate", getLastDestinationDate())
            .append("destinationTime", getDestinationTime())
            .append("changePlace", getChangePlace())
            .append("territoryDay", getTerritoryDay())
            .append("totalDay", getTotalDay())
            .append("isEdit", getIsEdit())
            .append("delayReason", getDelayReason())
            .append("changeDate", getChangeDate())
            .append("classEastAndWest", getClassEastAndWest())
            .append("classBlockTrain", getClassBlockTrain())
            .append("classStationofdestinationName", getClassStationofdestinationName())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("classDate", getClassDate())
            .append("port", getPort())
            .toString();
    }
}
