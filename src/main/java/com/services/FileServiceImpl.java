package com.services;

import ch.qos.logback.classic.Logger;
import com.alibaba.excel.EasyExcel;
import com.config.UserListener;
import com.entity.ExcelUserDTO;
import com.mapper.UserMapper;
import com.services.Impl.FileService;
import com.utils.Result;
import com.utils.ResultEnum;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：FileServiceImpl
 * @Date：3/11/2023 4:05 PM
 * @Filename：FileServiceImpl
 */
@Service
public class FileServiceImpl implements FileService {
    @Resource
    private UserMapper userMapper;

    @Value("${custom.file.uploadAddress}")
    private  String filepath;
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    /**
     * 上传Excel
     * @param multipartFile
     * @return
     */
    @Override
    public Result uploadExcel(MultipartFile multipartFile) throws IOException {
        /**
         * 直接转换成file，
         */
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        File file = null;
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            file = File.createTempFile(fileName, prefix);
            multipartFile.transferTo(file);
            //返回的File文件
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug(prefix);
        if (".xlsx".equals(prefix)){
            EasyExcel.read(file, ExcelUserDTO.class, new UserListener(userMapper)).sheet().doRead();
            return new Result(ResultEnum.SUCCESS,"Success");
        }
        return new Result(ResultEnum.FAIL,"文件类型不为Excel");
    }

    @Override
    public String imageUpload(MultipartFile file, HttpServletRequest request) {
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
