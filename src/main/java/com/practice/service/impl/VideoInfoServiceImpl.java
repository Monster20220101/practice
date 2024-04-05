package com.practice.service.impl;

import cn.hutool.core.map.MapBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.common.dto.UserDto;
import com.practice.common.dto.VideoDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.BaseVo;
import com.practice.common.vo.VideoVo;
import com.practice.entity.ImgsInfo;
import com.practice.entity.VideoInfo;
import com.practice.mapper.VideoInfoMapper;
import com.practice.service.ImgsInfoService;
import com.practice.service.VideoInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.practice.utils.MyFileUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 视频表 服务实现类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Service
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoMapper, VideoInfo> implements VideoInfoService {
    @Autowired
    ImgsInfoService imgsInfoService;
    @Override
    public Result addVideo(VideoVo vo, MultipartFile[] files) throws IOException {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        Long imgsId = MyFileUtil.uploadFiles(files, "videoInfo");
        ImgsInfo imgsInfo = imgsInfoService.getOne(new QueryWrapper<ImgsInfo>()
                .eq("imgs_id", imgsId).last("limit 1"));

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setTitle(vo.getTitle())
                .setVideoUrl(imgsInfo.getImgUrl())
                .setCreatedBy(userDto.getId())
                .setRelationClassId(userDto.getStudentInfo().getRelationClassId());
        boolean save = super.save(videoInfo);
        return save ? Result.success("添加成功") : Result.error("添加失败");
    }

    @Override
    public Result deletedVideo(VideoVo vo) {
        VideoInfo videoInfo = super.getById(vo.getId());
        if(videoInfo==null || videoInfo.getIsDeleted()==BaseVo.DELETED) {
            return Result.error("视频不存在或已删除");
        }
        videoInfo.setIsDeleted(BaseVo.DELETED);
        boolean b = super.updateById(videoInfo);
        return b ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Override
    public Result getVideoList(VideoVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        if(userDto.getUserType()==2) {
            vo.setUserId(userDto.getId());
        } else if (userDto.getUserType()==1){
            vo.setTeacherId(userDto.getTeacherInfo().getId());
        }
        List<VideoDto> list = super.baseMapper.getListPage(vo);
        Integer total = super.baseMapper.getListPageCount(vo);

        return Result.success("请求成功", MapBuilder.create()
                .put("list", list)
                .put("total", total)
                .put("pages", BaseVo.calculatePages(vo.getSize(), total))
                .map());
    }
}
