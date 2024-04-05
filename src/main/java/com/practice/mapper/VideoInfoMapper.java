package com.practice.mapper;

import com.practice.common.dto.VideoDto;
import com.practice.common.vo.VideoVo;
import com.practice.entity.VideoInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 视频表 Mapper 接口
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Repository
public interface VideoInfoMapper extends BaseMapper<VideoInfo> {

    List<VideoDto> getListPage(VideoVo vo);

    Integer getListPageCount(VideoVo vo);
}
