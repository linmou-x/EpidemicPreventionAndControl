package com.utils;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * @Author：Charles
 * @Package：com.utils
 * @Project：EpidemicPreventionAndControl
 * @name：ImageUtil
 * @Date：2023/1/6 15:29
 * @Filename：ImageUtil
 */
@Configuration
public class ImageUtil {
    @Value("${custom.file.uploadAddress}")
    private  String filepath;
    public String imageUpload(MultipartFile file, HttpServletRequest request){

        String url=null;
        if (file.isEmpty()){
            return "空文件上传";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片，写到需要保存的目录下
//        String filePath = "S:/Download/";

        try {
            //将图片保存到文件夹里
            file.transferTo(new File(filepath+fileName));
//            file.transferTo(new File(filepath)); // 保存文件
            url = request.getScheme() + "://" + request.getServerName() +
                    ":" + request.getServerPort() + "/Pictures/"+ fileName;
            //返回提示信息
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }


}