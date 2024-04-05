package com.practice.controller;


import com.practice.common.lang.Result;
import com.practice.common.vo.TaskVo;
import com.practice.service.TaskInfoService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 任务管理 前端控制器
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/taskInfo")
public class TaskInfoController {
    @Autowired
    private TaskInfoService taskInfoService;

    @RequiresAuthentication
    @RequiresRoles("teacher")
    @PostMapping("addTask")
    public Result addTask(@RequestBody @Validated TaskVo vo){
        return taskInfoService.addTask(vo);
    }

    @RequiresAuthentication
    @RequiresRoles("teacher")
    @PutMapping("modifyTask")
    public Result modifyTask(@RequestBody TaskVo vo){
        if(vo.getId()==null){
            return Result.error("缺少id");
        }
        return taskInfoService.modifyTask(vo);
    }

    @RequiresAuthentication
    @RequiresRoles("teacher")
    @DeleteMapping("deletedTask")
    public Result deletedTask(@RequestBody TaskVo vo){
        if(vo.getId()==null){
            return Result.error("缺少id");
        }
        return taskInfoService.deletedTask(vo);
    }

    @RequiresAuthentication
    @RequiresRoles("teacher")
    @GetMapping("getTaskManagerList")
    public Result getTaskManagerList(TaskVo vo){
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getPage()==null){
            return Result.error("缺少page");
        }
        if(vo.getSize()==null){
            return Result.error("缺少size");
        }
        return taskInfoService.getTaskManagerList(vo);
    }

    @RequiresAuthentication
    @GetMapping("getTaskById")
    public Result getTaskById(TaskVo vo){
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getId()==null){
            return Result.error("缺少id");
        }
        return taskInfoService.getTaskById(vo);
    }

    /**
     *
     * @param vo
     * @return
     */
    @RequiresAuthentication//限制用户登录
    @RequiresRoles("teacher")
    @GetMapping("getStudentScoreList")
    public Result getStudentScoreList(TaskVo vo){
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getId()==null){
            return Result.error("缺少id");
        }
        return taskInfoService.getStudentScoreList(vo);
    }

    @RequiresAuthentication
    @RequiresRoles("teacher")
    @PostMapping("score")
    public Result score(@RequestBody TaskVo vo){ //json:{"id":"1232131", "name":"asdfsadf"}
        if(vo.getId()==null){
            return Result.error("缺少id");
        }
        if(vo.getStudentId() == null) {
            return Result.error("缺少学生id");
        }
        if(vo.getScore() == null){
            return Result.error("缺少分数");
        }
        return taskInfoService.score(vo);
    }

    @RequiresAuthentication
    @RequiresRoles("student")
    @GetMapping("getTaskListForStudent")
    public Result getTaskListForStudent(TaskVo vo){
        if(vo==null){
            return Result.error("缺少参数");
        }
        if(vo.getPage()==null){
            return Result.error("缺少page");
        }
        if(vo.getSize() == null) {
            return Result.error("缺少size");
        }
        return taskInfoService.getTaskListForStudent(vo);
    }
}
