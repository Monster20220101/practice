package com.practice.timeTask;

import com.practice.service.TaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务：查询数据最小原则、执行sql最少原则
 */
@Component
public class TaskTimeTask {
    @Autowired
    private TaskInfoService taskInfoService;

    // Cron表达式生成器 https://cron.qqe2.com/
    // 秒 分 时 日 月 (星期) 年份
    // @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 1 * * * ?")
    public void UpdateTaskStatus(){
        taskInfoService.updateTaskInfoTimeTask();
    }
}
