package com.practice.controller;


import com.practice.common.lang.Result;
import com.practice.common.vo.TeacherVo;
import com.practice.common.vo.UserVo;
import com.practice.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public Result login(HttpServletResponse response, @Validated @RequestBody UserVo vo){
        return userInfoService.login(response, vo);
    }

    @PostMapping("/register")
    public Result register(HttpServletResponse response, @Validated @RequestBody TeacherVo vo){
        return userInfoService.register(response, vo);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success("退出成功");
    }

    @RequiresAuthentication
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UserVo vo){
        return userInfoService.updatePassword(vo.getPassword(), vo.getNewPassword());
    }

    @RequiresAuthentication
    @PostMapping("/updateTelephone")
    public Result updateTelephone(@RequestBody UserVo vo) {
        return userInfoService.updateTelephone(vo.getTelephone());
    }

    @RequiresAuthentication
    @PostMapping("/updateUserAva")
    public Result updateUserAva(@RequestParam(value = "files")
                                             MultipartFile[] files) throws IOException {
        if(files.length==0){ // 试试@NotEmpty?
            return Result.error("缺少参数");
        }
        return userInfoService.updateUserAva(files);
    }
}
