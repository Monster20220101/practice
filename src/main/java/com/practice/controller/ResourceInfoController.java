package com.practice.controller;


import com.practice.common.lang.Result;
import com.practice.common.vo.ResourceVo;
import com.practice.service.ResourceInfoService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/resourceInfo")
public class ResourceInfoController {

    @Autowired
    private ResourceInfoService resourceInfoService;

    @RequiresAuthentication
    @RequiresRoles("teacher")
    @PostMapping("addResource")
    public Result addResource(@Validated ResourceVo vo,
                              @RequestParam(value = "files") MultipartFile[] files) throws IOException {
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getRelationClassId()==null){
            return Result.error("缺少班级id");
        }
        return resourceInfoService.addResource(vo, files);
    }

    @RequiresAuthentication
    @RequiresRoles("teacher")
    @DeleteMapping("deletedResource")
    public Result deletedResource(@RequestBody ResourceVo vo) {
        if(vo.getId()==null){
            return Result.error("缺少id");
        }
        return resourceInfoService.deletedResource(vo);
    }

    @RequiresAuthentication
    @GetMapping("getResourceList")
    public Result getResourceList(ResourceVo vo) {
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getPage() == null){
            return Result.error("缺少页数");
        }
        if(vo.getSize() == null){
            return Result.error("缺少size");
        }
        return resourceInfoService.getResourceList(vo);
    }
}
