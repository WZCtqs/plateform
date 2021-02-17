package com.szhbl.project.cabinarrangement.vo;

import java.util.List;

public class PaiCangDetail {
    private String station;
    private String kg;
    private String val;
    private List<Box> list;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public List<Box> getList() {
        return list;
    }

    public void setList(List<Box> list) {
        this.list = list;
    }
}
