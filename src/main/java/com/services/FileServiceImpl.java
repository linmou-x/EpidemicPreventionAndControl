package com.services;

import com.services.Impl.FileService;
import com.utils.Result;
import org.springframework.web.multipart.MultipartFile;

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
     * @param multipartFile
     * @return
     */
    @Override
    public Result uploadExcel(MultipartFile multipartFile) {
        return null;
    }
}
