package org.Chr.service;

import org.Chr.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    ResponseResult upload(MultipartFile img);
}
