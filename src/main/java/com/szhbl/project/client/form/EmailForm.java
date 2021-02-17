package com.szhbl.project.client.form;

import lombok.Data;


@Data
public class EmailForm {
    private String title;
    private String sendUserEmaill;
    private String emailPass;
    private String content;
    private String registerStartTime;
    private String registerEndTime;

}
