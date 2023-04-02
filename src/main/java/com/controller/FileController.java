package com.controller;

import com.services.Impl.FileService;
import com.utils.ImageUtil;
import com.utils.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
@CrossOrigin(origins = "*")
public class FileController {
    @Resource
    ImageUtil imageUtil;
    @Resource
    private FileService fileService;

    @PostMapping("/uploadExcel")
    public Result uploadExcel(@RequestParam(value = "file")MultipartFile excel) throws IOException {
        return fileService.uploadExcel(excel);
    }


    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam(value = "file")MultipartFile image, HttpServletRequest httpServletRequest){
        return imageUtil.imageUpload(image,httpServletRequest);
    }
}
