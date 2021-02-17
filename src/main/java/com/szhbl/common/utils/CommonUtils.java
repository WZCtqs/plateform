package com.szhbl.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ResourceLoader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CommonUtils {



    /**
     * 下载导入模板
     * @param filename
     * @param path
     * @param request
     * @param response
     */
    public static void downloadThymeleaf(ResourceLoader resourceLoader,String filename, String path, HttpServletRequest request, HttpServletResponse response){
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {


            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:"+path);
            String agent = request.getHeader("USER-AGENT").toLowerCase();
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            if (agent.contains("firefox")) {
                response.setCharacterEncoding("utf-8");
                response.setHeader("content-disposition", "attachment;filename=" + new String(encodeName.getBytes(), "ISO8859-1") + ".xls" );
            } else {
                response.setHeader("content-disposition", "attachment;filename=" + encodeName + ".xls");
            }

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            log.error("下载导入模板失败：{}",e.toString(),e.getStackTrace());
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                    servletOutputStream = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                log.error("下载导入模板关闭流失败：{}",e.toString(),e.getStackTrace());
            }
        }

    }
    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("判断对象中属性值是否全为空失败：{}",e.toString(),e.getStackTrace());
        }
        return true;
    }

}
