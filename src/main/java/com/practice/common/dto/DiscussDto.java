package com.practice.common.dto;

import com.practice.entity.DiscussInfo;
import com.practice.entity.ImgsInfo;
import lombok.Data;

import java.util.List;

@Data
public class DiscussDto extends DiscussInfo {

    /**
     * 姓名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAva;

    /**
     * 关联图片集url
     */
    private List<String> imgsInfoUrlList;
}
