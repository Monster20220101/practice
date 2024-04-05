package com.practice.controller;


import com.practice.common.lang.Result;
import com.practice.common.vo.StudentVo;
import com.practice.service.StudentInfoService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/studentInfo")
public class StudentInfoController {
    @Autowired
    private StudentInfoService studentInfoService;

    //增
    @RequiresAuthentication
    @RequiresRoles("teacher")
    @PostMapping("/add")
    public Result addStudent(@Validated @RequestBody StudentVo vo){
        if(vo.getRelationClassId() == null){
            return Result.error("缺少班级id");
        }
        return studentInfoService.addStudent(vo);
    }

    //改
    @RequiresAuthentication
    @RequiresRoles("teacher")
    @PutMapping("/modify")
    public Result modifyStudent(@RequestBody StudentVo vo){
        if(vo.getId() == null){
            return Result.error("缺少学生id");
        }
        return studentInfoService.modifyStudent(vo);
    }

    //删
    @RequiresAuthentication
    @RequiresRoles("teacher")
    @DeleteMapping("/deleted")
    public Result deletedStudent(@RequestBody StudentVo vo){
        if(vo.getId() == null) {
            return Result.error("缺少学生id");
        }
        return studentInfoService.deletedStudent(vo);
    }

    //查
    @RequiresAuthentication
    @RequiresRoles("teacher")
    @GetMapping("/getStudentList")
    public Result getStudentList(StudentVo vo){
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getPage() == null){
            return Result.error("缺少页数");
        }
        if(vo.getSize() == null){
            return Result.error("缺少size");
        }
        return studentInfoService.getStudentList(vo);
    }
}
