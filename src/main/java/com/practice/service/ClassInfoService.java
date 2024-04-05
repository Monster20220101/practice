package com.practice.service;

import com.practice.common.lang.Result;
import com.practice.common.vo.ClassVo;
import com.practice.entity.ClassInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 班级表 服务类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
public interface ClassInfoService extends IService<ClassInfo> {

    /**
     * 添加班级
     * @param vo
     * @return
     */
    Result addClass(ClassVo vo);

    /**
     * 修改班级
     * @param vo
     * @return
     */
    Result modify(ClassVo vo);

    /**
     * 删除班级
     * @param vo
     * @return
     */
    Result deleted(ClassVo vo);

    /**
     * 获取班级列表
     * @param vo
     * @return
     */
    Result getClassList(ClassVo vo);
}
