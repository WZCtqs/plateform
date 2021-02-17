package com.szhbl.project.customerservice.vo;

import java.util.List;

public class InfoVo {

    private String orderNumber;
    private String orderId;
    private String clientTjr;
    private String clientTjrId;
    private String clientId;
    private String clientUnit;
    /** 0为去程 1为回程 */
    private String classEastandwest;
    /** 服务：0门到门 1门到站 2站到站 3站到门 */
    private String bookingService;
    /** 是否可堆叠（1是0否2仅可自叠） */
    private String goodsCannotpileup;
    /** 最外层包装形式 */
    private String goodsPacking;
    /** 货品中文名称 */
    private String goodsName;
    /** 货品英文名称 */
    private String goodsEnName;
    //接货照
    private List<String> pickPicture;
    //装箱照
    private List<String> boxingPicture;
    //拆箱照
    private List<String> unpackingPicture;

    /** 班列ID */

    private String classId;
    //站到站运行时间
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientTjr() {
        return clientTjr;
    }

    public void setClientTjr(String clientTjr) {
        this.clientTjr = clientTjr;
    }

    public String getClientTjrId() {
        return clientTjrId;
    }

    public void setClientTjrId(String clientTjrId) {
        this.clientTjrId = clientTjrId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientUnit() {
        return clientUnit;
    }

    public void setClientUnit(String clientUnit) {
        this.clientUnit = clientUnit;
    }

    public String getClassEastandwest() {
        return classEastandwest;
    }

    public void setClassEastandwest(String classEastandwest) {
        this.classEastandwest = classEastandwest;
    }

    public String getBookingService() {
        return bookingService;
    }

    public void setBookingService(String bookingService) {
        this.bookingService = bookingService;
    }

    public String getGoodsCannotpileup() {
        return goodsCannotpileup;
    }

    public void setGoodsCannotpileup(String goodsCannotpileup) {
        this.goodsCannotpileup = goodsCannotpileup;
    }

    public String getGoodsPacking() {
        return goodsPacking;
    }

    public void setGoodsPacking(String goodsPacking) {
        this.goodsPacking = goodsPacking;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsEnName() {
        return goodsEnName;
    }

    public void setGoodsEnName(String goodsEnName) {
        this.goodsEnName = goodsEnName;
    }

    public List<String> getPickPicture() {
        return pickPicture;
    }

    public void setPickPicture(List<String> pickPicture) {
        this.pickPicture = pickPicture;
    }

    public List<String> getBoxingPicture() {
        return boxingPicture;
    }

    public void setBoxingPicture(List<String> boxingPicture) {
        this.boxingPicture = boxingPicture;
    }

    public List<String> getUnpackingPicture() {
        return unpackingPicture;
    }

    public void setUnpackingPicture(List<String> unpackingPicture) {
        this.unpackingPicture = unpackingPicture;
    }
}
