package com.practice.service;

import com.practice.common.lang.Result;
import com.practice.common.vo.VideoVo;
import com.practice.entity.VideoInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 视频表 服务类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
public interface VideoInfoService extends IService<VideoInfo> {

    Result addVideo(VideoVo vo, MultipartFile[] files) throws IOException;

    Result deletedVideo(VideoVo vo);

    Result getVideoList(VideoVo vo);
}
