package org.Chr.service.impl;

import org.Chr.Utils.OssUpload;
import org.Chr.domain.Enum.AppHttpCodeEnum;
import org.Chr.domain.ResponseResult;
import org.Chr.exception.SystemException;
import org.Chr.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class UploadServiceImpl implements UploadService {

    @Resource
    OssUpload ossUpload;
    @Override
    public ResponseResult upload(MultipartFile img) {
        //判断格式类型
        String originalFilename = img.getOriginalFilename();

        if(!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.IMG_FILE_ERROR);
        }
        //上传图片
        String url = ossUpload.ossUpload(img,originalFilename);
        return ResponseResult.okResult(url);
    }
}
