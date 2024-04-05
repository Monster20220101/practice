package com.practice.controller;

import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.common.lang.Result;
import com.practice.entity.ImgsInfo;
import com.practice.service.ImgsInfoService;
import com.practice.utils.MyFileUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file")
public class FileController {

    // @PostMapping("upload")
    // public CommonResult uploadFileAjax(HttpServletRequest request) throws IOException {
    //     MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
    //     MultipartFile file = multiRequest.getFile("file");
    //     String url = UploadFileUtil.localSave(file);
    //     return CommonResult.success("上传成功",url);
    // }

}
