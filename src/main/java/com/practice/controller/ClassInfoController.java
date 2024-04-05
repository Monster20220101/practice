package com.practice.controller;


import cn.hutool.http.HttpRequest;
import com.practice.common.lang.Result;
import com.practice.common.vo.ClassVo;
import com.practice.service.ClassInfoService;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/classInfo")
public class ClassInfoController {
    @Autowired
    private ClassInfoService classInfoService;

    /**
     * 添加班级
     * @param vo
     * @return
     */
    @RequiresAuthentication
    @RequiresRoles("teacher")
    @PostMapping("add")
    //接收参数的时候进行判断
    public Result addClass(@RequestBody @Validated ClassVo vo){ //json: {"className":"xxx", "id":"xxxx"}
        return classInfoService.addClass(vo);
    }

    /**
     * 编辑班级
     * @param vo
     * @return
     */
    @RequiresAuthentication
    @RequiresRoles("teacher")
    @PutMapping("modify")
    public Result modifyClass(@RequestBody ClassVo vo){
        if(vo.getId()==null){
            return Result.error("缺少班级id");
        }
        return classInfoService.modify(vo);
    }

    /**
     * 删除班级
     * @param vo
     * @return
     */
    @RequiresAuthentication
    @RequiresRoles("teacher")
    @DeleteMapping("deleted")
    public Result deletedClass(@RequestBody ClassVo vo){
        return classInfoService.deleted(vo);
    }

    /**
     * 获取班级列表（分页/不分页）
     * @param vo
     * @return
     */
    @RequiresAuthentication
    @RequiresRoles("teacher")
    @GetMapping("getClassList")
    public Result getClassList(ClassVo vo, HttpServletRequest req){
        if(vo==null){
            return Result.error("缺少参数");
        }
        return classInfoService.getClassList(vo);
    }
}
