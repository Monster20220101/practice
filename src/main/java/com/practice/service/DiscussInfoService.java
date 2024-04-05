package com.practice.service;

import com.practice.common.lang.Result;
import com.practice.common.vo.DiscussVo;
import com.practice.entity.DiscussInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 答疑表 服务类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
public interface DiscussInfoService extends IService<DiscussInfo> {

    Result addDiscuss(DiscussVo vo, MultipartFile[] files) throws IOException;

    Result deletedDiscuss(DiscussVo vo);

    Result getDiscussList(DiscussVo vo);

    Result getReplyList(DiscussVo vo);
}
