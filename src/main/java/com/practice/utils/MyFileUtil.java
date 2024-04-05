package com.practice.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.practice.common.dto.UserDto;
import com.practice.entity.ImgsInfo;
import com.practice.service.ImgsInfoService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class MyFileUtil {
    static ImgsInfoService imgsInfoService;

    @Autowired
    public void setImgsService(ImgsInfoService imgsInfoService) {
        MyFileUtil.imgsInfoService = imgsInfoService;
    }

    public static String BASE_URL = "D:/practice/";
    public static String ONLINE_URL = "http://localhost:8082/d/practice/";

    /*
    @RequestParam("file") MultipartFile file

    String userHead = MyFileUtil.uploadFile(file, "userHead");
    user.setUserHead(userHead);
     */
    // 文件上传 直接使用无法存数据库！
    public static String uploadFile(MultipartFile file, String dir)
            throws IllegalStateException, IOException {
        if (file != null && file.getSize() > 0) {
            System.out.println(file.getOriginalFilename());
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            long id = snowflake.nextId();
            String newFileName =
                    FileUtil.mainName(file.getOriginalFilename())
                            + id + "."
                            + FileUtil.extName(file.getOriginalFilename());

            File targetFile = new File(BASE_URL + dir + "/" + newFileName);
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdir();
            }
            file.transferTo(targetFile);
            return ONLINE_URL + dir + "/" + newFileName;
        } else {
            return "fail";
        }
    }

    /*
    @RequestParam("addimg") Mult..[] files

    int imgsId = MyFileUtil.uploadFiles(files, "product");
    if(imgsId != 0){
        product.setImgsId(imgsId);
    }
     */
    // 多个文件上传
    public static long uploadFiles(MultipartFile[] files, String dir) throws IllegalStateException, IOException {
        if (files != null && files.length > 0) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            long imgsId = snowflake.nextId();
            for (int i = 0; i < files.length; i++) {
                String imgUrl = uploadFile(files[i], dir);
                if ("fail".equals(imgUrl)) {
                    return 0;
                }
                ImgsInfo imgsInfo = new ImgsInfo();
                imgsInfo.setImgUrl(imgUrl);
                imgsInfo.setImgsId(imgsId);

                UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
                imgsInfo.setCreateBy(userDto.getId());

                try {
                    imgsInfoService.save(imgsInfo);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            return imgsId;
        } else {
            return 0;
        }
    }

}
