package com.practice.controller;


import com.practice.common.lang.Result;
import com.practice.common.vo.VideoVo;
import com.practice.service.VideoInfoService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 视频表 前端控制器
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/videoInfo")
public class VideoInfoController {

    @Autowired
    private VideoInfoService videoInfoService;

    @RequiresAuthentication
    @RequiresRoles("student")
    @PostMapping("addVideo")
    public Result add(@Validated VideoVo vo,
                      @RequestParam(value = "files") MultipartFile[] files) throws IOException {
        return videoInfoService.addVideo(vo, files);
    }

    @RequiresAuthentication // shiro-登录用户才能访问
    @RequiresRoles("student") // shiro-student才能访问
    @DeleteMapping("deletedVideo")
    public Result deleted(@RequestBody VideoVo vo) {
        if (vo.getId()==null) {
            return Result.error("缺少id");
        }
        return videoInfoService.deletedVideo(vo);
    }

    @RequiresAuthentication
    @GetMapping("getVideoList")
    public Result getList(VideoVo vo) {
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getPage() == null){
            return Result.error("缺少页数");
        }
        if(vo.getSize() == null){
            return Result.error("缺少size");
        }
        return videoInfoService.getVideoList(vo);
    }
}
