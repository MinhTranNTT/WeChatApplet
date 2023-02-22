package com.zw.backstage.controller;

import com.zw.common.utils.JsonResult;
import com.zw.common.utils.UploadFile;
import com.zw.common.utils.annotation.Authorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FIleController {
    @Autowired
    UploadFile uploadFile;

    //上传文件
//    @Authorize
    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file){
        String path ="/"+"product";
        String url = uploadFile.saveFile(file,path);
        return url;
    }

    //删除文件
    @Authorize
    @PostMapping("/delete")
    public JsonResult<Boolean> delete(@RequestBody String url){
        // 将url从file分割出来
        String filePath = url.split("file/")[1];
        filePath = filePath.split("\"}")[0];
        return new JsonResult<>(200,uploadFile.deleteFile(filePath));
    }

}
