package com.szhbl.common.utils.file.qiniu;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Johnson
 * @date 2019/12/14/ 17:20:16
 * 上传文件到七牛云
 */
@Slf4j
public class UploadFileQiniu implements UploadFile {

    private UploadProperties.Qiniu properties;

    //构造一个带指定Region对象的配置类
    private Configuration cfg = new Configuration(Region.region0());

    private UploadManager uploadManager= new UploadManager(cfg);

    public UploadFileQiniu(UploadProperties.Qiniu properties) {
        this.properties = properties;
    }

    /**
     * @author: Johnson
     */
    @Override
    public String uploadFile(MultipartFile file) {
        Auth auth = Auth.create(properties.getAccessKey(), properties.getSecretKey());
        String token = auth.uploadToken(properties.getBucket());
        try {
            String fileKey = UUID.randomUUID().toString() + ".jpg";
            Response response = uploadManager.put(file.getInputStream(), fileKey, token, null, null);
            if(response.statusCode == 200){
                return "http://"+properties.getDomain() +"/" +fileKey;
            }else {
                return "error";
            }
        } catch (IOException e) {
            log.error("上传文件到七牛云失败：{}",e.toString(),e.getStackTrace());
        }
        return "error";
    }
}
