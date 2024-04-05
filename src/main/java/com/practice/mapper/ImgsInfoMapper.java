package com.practice.mapper;

import com.practice.entity.ImgsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 第五组
 * @since 2022-10-21
 */
@Repository
public interface ImgsInfoMapper extends BaseMapper<ImgsInfo> {
    List<String> getUrlListByImgsId(Long relationImgsId);
}
