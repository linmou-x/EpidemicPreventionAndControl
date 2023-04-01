package com.controller;

import com.services.Impl.FileService;
import com.utils.Result;
import com.utils.ResultEnum;
import org.apache.logging.log4j.message.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author：Charles
 * @Package：com.controller
 * @Project：EpidemicPreventionAndControl
 * @name：FileController
 * @Date：3/11/2023 3:59 PM
 * @Filename：FileController
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping("/uploadExcel")
    public Result uploadExcel(@RequestParam(value = "file")MultipartFile excel) throws IOException {
        return fileService.uploadExcel(excel);
    }
}
