package com.practice.service;

import com.practice.common.lang.Result;
import com.practice.common.vo.TeacherVo;
import com.practice.common.vo.UserVo;
import com.practice.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.MulticastChannel;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 用户登录
     * @param response 响应
     * @param vo 参数映射
     * @return 通用返回
     */
    Result login(HttpServletResponse response, UserVo vo);

    /**
     * 用户注册
     * @param response 响应
     * @param vo 参数映射
     * @return 通用返回
     */
    Result register(HttpServletResponse response, TeacherVo vo);

    /**
     * 修改密码
     * @param password
     * @return
     */
    Result updatePassword(String password, String newPassword);

    /**
     * 修改用户头像
     * @param files
     * @return
     * @throws IOException
     */
    Result updateUserAva(MultipartFile[] files) throws IOException;

    /**
     * 修改用户手机号
     * @param telephone
     * @return
     */
    Result updateTelephone(String telephone);
}
