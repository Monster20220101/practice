package com.practice.controller;


import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 第五组
 * @since 2022-10-21
 */
@RestController   //注册层controller
@RequestMapping("/imgsInfo")
public class ImgsInfoController {

    @GetMapping("/getFile")
    public void getFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //读取路径下面的文件
        String filePath = request.getParameter("filePath");
        String name = request.getParameter("name");
        filePath = "D:" + filePath.substring(23);
        String suffix = filePath.substring(filePath.lastIndexOf("."));
        System.err.println("文件来源：" + filePath);
        System.err.println("文件下载名：" + name + suffix);

        File file = new File(filePath);
        response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(name, "utf-8") + suffix);
        //读取指定路径下面的文件
        InputStream in = new FileInputStream(file);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        //创建存放文件内容的数组
        byte[] buff =new byte[1024];
        //所读取的内 容使用n来接收
        int n;
        //当没有读取完时,继续读取,循环
        while((n=in.read(buff))!=-1){
            //将字节数组的数据全部写入到输出流中
            outputStream.write(buff,0,n);
        }
        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
        in.close();
    }
}
