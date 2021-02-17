package com.szhbl.project.order.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class RecordObj {
    private String title;
    private String[] list;
    private List<RecordObjCon> contentList;
}