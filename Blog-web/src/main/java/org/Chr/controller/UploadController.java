package org.Chr.controller;

import org.Chr.domain.ResponseResult;
import org.Chr.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile img) {
        return uploadService.upload(img);
    }
}
