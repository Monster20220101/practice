package com.practice.mapper;

import com.practice.common.dto.DiscussDto;
import com.practice.common.vo.DiscussVo;
import com.practice.entity.DiscussInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 答疑表 Mapper 接口
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Repository
public interface DiscussInfoMapper extends BaseMapper<DiscussInfo> {

    List<DiscussDto> getListPage(DiscussVo vo);

    Integer getListPageCount(DiscussVo vo);

    List<DiscussDto> getReplyList(DiscussVo vo);
}
