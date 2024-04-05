package com.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.common.dto.UserDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.BaseVo;
import com.practice.common.vo.TeacherVo;
import com.practice.common.vo.UserVo;
import com.practice.entity.ImgsInfo;
import com.practice.entity.StudentInfo;
import com.practice.entity.TeacherInfo;
import com.practice.entity.UserInfo;
import com.practice.mapper.UserInfoMapper;
import com.practice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.practice.utils.JwtUtils;
import com.practice.utils.MyFileUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    TeacherInfoService teacherInfoService;
    @Autowired
    StudentInfoService studentInfoService;
    @Autowired
    ImgsInfoService imgsInfoService;
    @Autowired
    ClassInfoService classInfoService;

    @Override
    public Result login(HttpServletResponse response, UserVo vo) {
        UserInfo userInfo = super.getOne(new QueryWrapper<UserInfo>()
                .eq("account_num", vo.getAccountNum()).eq("is_deleted", BaseVo.NOT_DELETED));
        if(userInfo == null){
            return Result.error("用户不存在");
        }
        if(userInfo.getUserStatus() == 1) {
            return Result.error("用户已注销");
        }
        if(!userInfo.getPassword().equals(SecureUtil.md5(vo.getPassword()))){
            return Result.error("账号或密码错误");
        }
        String jwt = jwtUtils.generateToken(userInfo.getId());

        // 为了后续的jwt的延期，所以把jwt放在header上
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(userInfo, userDto);

        // 根据userType查询不同角色信息，放入对象
        if(userDto.getUserType() == 1){ // teacher
            userDto.setTeacherInfo(teacherInfoService.getOne(new QueryWrapper<TeacherInfo>()
                    .eq("relation_user_id",userDto.getId())));
        } else if (userDto.getUserType() == 2){ // student
            userDto.setStudentInfo(studentInfoService.getOne(new QueryWrapper<StudentInfo>()
                    .eq("relation_user_id",userDto.getId())));
            Long classId = userDto.getStudentInfo().getRelationClassId();
            userDto.setClassName(classInfoService.getById(classId).getClassName());
        }

        return Result.success("登录成功", MapUtil.builder()
                .put("userDto", userDto)
                .map());
    }


    @Override
    public Result register(HttpServletResponse response, TeacherVo vo) {
        UserInfo userInfo = super.getOne(new QueryWrapper<UserInfo>()
                .eq("account_num", vo.getTeacherNum()).eq("is_deleted", BaseVo.NOT_DELETED));
        // 查重&&未注销-->返回“重复注册！”
        if(userInfo!=null&&userInfo.getUserStatus()==0){
            return Result.error("重复注册！");
        }
        if(userInfo!=null&&userInfo.getUserStatus()==1&&userInfo.getUserType()==2){
            return Result.error("学生禁止注册！");
        }
        // 查重&&已注销-->直接把该账号变成未注销
        if(userInfo!=null&&userInfo.getUserStatus()==1){
            userInfo.setUserStatus(0);
            super.updateById(userInfo);
        } else {// else -->存入数据库
            String password = SecureUtil.md5(vo.getPassword());
            boolean save1 = super.save(new UserInfo()
                    .setAccountNum(vo.getTeacherNum())
                    .setPassword(password)
                    .setUserType(1)
                    .setTelephone(vo.getTelephone()));
            if(!save1){
                return Result.error("注册失败");
            }

            userInfo = super.getOne(new QueryWrapper<UserInfo>()
                    .eq("account_num", vo.getTeacherNum()));
            TeacherInfo teacherInfo = new TeacherInfo();
            BeanUtil.copyProperties(vo, teacherInfo);
            teacherInfo.setRelationUserId(userInfo.getId());
            boolean save2 = teacherInfoService.save(teacherInfo);
            if(!save2){
                return Result.error("注册失败");
            }
        }

        // 获取信息返回给前端
        String jwt = jwtUtils.generateToken(userInfo.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(userInfo, userDto);

        // 只有老师能注册，所以：创造teacher存入数据库
        userDto.setTeacherInfo(teacherInfoService.getOne(new QueryWrapper<TeacherInfo>()
                .eq("relation_user_id", userDto.getId())));

        return Result.success("注册成功", MapUtil.builder()
                .put("userDto", userDto)
                .map());
    }

    @Override
    public Result updatePassword(@NotBlank(message = "缺少参数") String password
            , @NotBlank(message = "缺少参数") String newPassword) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        UserInfo userInfo = super.getById(userDto.getId());
        if(!userInfo.getPassword().equals(SecureUtil.md5(password))){
            return Result.error("旧密码错误");
        }

        userInfo.setPassword(SecureUtil.md5(newPassword));
        boolean b = super.updateById(userInfo);
        if(!b){
            return Result.error("更新密码失败");
        }
        // 更新缓存
        // 不用更新 每SecurityUtils.getSubject().getPrincipal()一次就是调用UserReam的doGetAuthenticationInfo
        // 所以相当于又从数据库获取了一次（那里有从数据库获取的代码
        // 具体可参考 https://cloud.tencent.com/developer/article/1864726?from=15425
        // 第二个参考 https://veddy.cn/article/5
        return Result.success("更新密码成功");
    }

    @Override
    public Result updateUserAva(MultipartFile[] files) throws IOException {
        Long imgsId = MyFileUtil.uploadFiles(files, "userInfo");
        ImgsInfo imgsInfo = imgsInfoService.getOne(new QueryWrapper<ImgsInfo>()
                .eq("imgs_id", imgsId).last("limit 1"));
        // 获取缓存中的用户
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        userDto.setUserAva(imgsInfo.getImgUrl());
        // 更新
        boolean b = super.updateById(userDto);
        if(!b){
            return Result.error("修改失败");
        }

        return Result.success("修改成功", userDto);
    }

    @Override
    public Result updateTelephone(@Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号")
                                              String telephone) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        userDto.setTelephone(telephone);
        UserInfo userInfo = super.getById(userDto.getId());
        userInfo.setTelephone(telephone);
        boolean b = super.updateById(userInfo);
        if(!b){
            return Result.error("更新手机号失败");
        }
        return Result.success("更新手机号成功", userDto);
    }


}
