package com.myblog.controller;

import com.myblog.config.RequiredRoles;
import com.myblog.result.Result;
import com.myblog.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @RequiredRoles({"user"})
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        log.info("上传文件：{}", file);
    try {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = UUID.randomUUID().toString() + extension;
        String filePath = aliOssUtil.upload(file.getBytes(), objectName,null);

        return Result.success(filePath);
    }  catch (IOException e) {
        log.error("上传失败:{}",e);
    }
    return null;
    }

}
