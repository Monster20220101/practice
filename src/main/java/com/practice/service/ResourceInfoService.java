package com.practice.service;

import com.practice.common.lang.Result;
import com.practice.common.vo.ResourceVo;
import com.practice.entity.ResourceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
public interface ResourceInfoService extends IService<ResourceInfo> {

    Result addResource(ResourceVo vo, MultipartFile[] files) throws IOException;

    Result deletedResource(ResourceVo vo);

    Result getResourceList(ResourceVo vo);
}
