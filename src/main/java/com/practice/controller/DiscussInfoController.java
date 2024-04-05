package com.practice.controller;


import com.practice.common.lang.Result;
import com.practice.common.vo.DiscussVo;
import com.practice.service.DiscussInfoService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 答疑表 前端控制器
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/discussInfo")
public class DiscussInfoController {

    @Autowired
    private DiscussInfoService discussInfoService;

    @RequiresAuthentication
    @PostMapping("addDiscuss")
    public Result addDiscuss(@Validated DiscussVo vo,
                             @RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException {
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getRelationClassId()==null){
            return Result.error("缺少班级id");
        }
        return discussInfoService.addDiscuss(vo, files);
    }

    @RequiresAuthentication
    @DeleteMapping("deletedDiscuss")
    public Result deletedDiscuss(@RequestBody DiscussVo vo){
        if(vo.getId()==null){
            return Result.error("缺少id");
        }
        return discussInfoService.deletedDiscuss(vo);
    }

    @RequiresAuthentication
    @GetMapping("getDiscussList")
    public Result getDiscussList(DiscussVo vo){
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getPage()==null){
            return Result.error("缺少page");
        }
        if(vo.getSize()==null){
            return Result.error("缺少size");
        }
        return discussInfoService.getDiscussList(vo);
    }

    @RequiresAuthentication
    @GetMapping("getReplyList")
    public Result getReplyList(DiscussVo vo){
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getRelationDiscussId()==null){
            return Result.error("缺少relationDiscussId");
        }
        return discussInfoService.getReplyList(vo);
    }
}
