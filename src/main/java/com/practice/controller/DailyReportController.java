package com.practice.controller;


import com.practice.common.dto.UserDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.DailyReportVo;
import com.practice.entity.DailyReport;
import com.practice.service.DailyReportService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 日报表 前端控制器
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/dailyReport")
public class DailyReportController {
    @Autowired
    private DailyReportService dailyReportService;

    @RequiresAuthentication
    @RequiresRoles("student")
    @PostMapping("addDailyReport")
    public Result addDailyReport(@RequestBody @Validated DailyReportVo vo) {
        // @Validated进行数据判断跳转到相关vo调用@NotBlank进行判断
        return dailyReportService.addDailyReport(vo);
    }

    // 学生-修改标题、内容
    // 教师-修改评语
    @RequiresAuthentication
    @PutMapping("modifyDailyReport")
    public Result modifyDailyReport(@RequestBody DailyReportVo vo) {
        // @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(即请求体中的数据的)
        // json格式如下：{“aaa”:“111”,“bbb”:“222”}

        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        if(vo.getId()==null){
            return Result.error("缺少日报id");
        }
        if(userDto.getUserType()==1){
            return dailyReportService.addCommentDailyReport(userDto, vo);
        } else if(userDto.getUserType()==2){
            return dailyReportService.modifyDailyReport(userDto, vo);
        } else {
            return Result.error("无权限");
        }
    }

    @RequiresAuthentication
    @RequiresRoles("student")
    @DeleteMapping("deletedDailyReport")
    public Result deletedDailyReport(@RequestBody DailyReportVo vo) {
        if(vo.getId() == null){
            return Result.error("缺少日报id");
        }
        return dailyReportService.deletedDailyReport(vo);
    }

    // 学生-只能看自己的日报
    // 教师-看自己创建的班级的所有日报
    @RequiresAuthentication
    @GetMapping("getList")
    public Result getList(DailyReportVo vo) {
        return dailyReportService.getDailyReportList(vo);
    }

}
