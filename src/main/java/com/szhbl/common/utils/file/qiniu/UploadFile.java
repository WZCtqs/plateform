package com.szhbl.common.utils.file.qiniu;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFile {

    String uploadFile(MultipartFile file);
}
