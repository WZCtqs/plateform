package com.szhbl.common.utils.qrcode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author HP
 */
@Slf4j
public class EncodeTest {
    public static void main(String[] args) {
        try {
            String contents = "第一个二维码";
            String path = "C:\\Users\\HP\\Desktop\\数字化班列\\入货通知图片\\demo";
            String fileName = "ZIHWE39030D";
            String url = path+"\\"+fileName+".jpg";
            System.out.println(url);
            QRCodeUtils.encodeQRCODE(contents, path, fileName);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("EncodeTest失败：{}",e.toString(),e.getStackTrace());
        }
    }

}
