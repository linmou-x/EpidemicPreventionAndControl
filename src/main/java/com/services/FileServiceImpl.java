package com.services;

import cn.hutool.core.io.FileTypeUtil;
import com.entity.User;
import com.services.Impl.FileService;
import com.utils.Result;
import com.utils.ResultEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：FileServiceImpl
 * @Date：3/11/2023 4:05 PM
 * @Filename：FileServiceImpl
 */
public class FileServiceImpl implements FileService {
    /**
     * 上传Excel
     * @param file
     * @return
     */
    @Override
    public Result uploadExcel(MultipartFile file) {
        String isExcel= FileTypeUtil.getType((InputStream) file);
        if (isExcel!="excel"){
            return new Result(ResultEnum.FAIL,"文件类型不为Excel");
        }
//        List<User> userList=excelUtil.simpleExcelRead(file,User.class);
        return null;
    }
}
