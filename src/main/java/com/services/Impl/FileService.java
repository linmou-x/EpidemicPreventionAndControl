package com.services.Impl;

import com.utils.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author：Charles
 * @Package：com.services.Impl
 * @Project：EpidemicPreventionAndControl
 * @name：FileService
 * @Date：3/11/2023 3:59 PM
 * @Filename：FileService
 */
public interface FileService {
    /**
     * 上传Excel
     * @param multipartFile
     * @return
     */
    public Result uploadExcel(MultipartFile multipartFile);
}
