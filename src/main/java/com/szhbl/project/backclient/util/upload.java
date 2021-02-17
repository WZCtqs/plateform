package com.szhbl.project.backclient.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class upload {

    public static String uploadFile(HttpServletRequest request, MultipartFile pictures, String dirName)
            throws IllegalStateException, IOException {

        String pathUrl = "E:/szhbl";// 存放的路径
        System.out.println(pathUrl);
        String bpath = "";
        // System.out.println(1111);
        String message = "fail";
        // 原始的文件名
        String filename = pictures.getOriginalFilename();
        // 获取扩展名
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        // 文件后缀判断
        // long newfileName = UID.next();
        String newfileName = UUID.randomUUID().toString();
        message = "upload" + "/" + dirName + "/" + newfileName + "." + extensionName;
        File mulu = new File(pathUrl + "/" + "upload" + "/" + dirName);
        if (!mulu.exists()) {
            mulu.mkdirs();
        }
        bpath = pathUrl + "/" + message;
        File file = new File(bpath);
        pictures.transferTo(file);
        return newfileName + "." + extensionName;

    }
}
