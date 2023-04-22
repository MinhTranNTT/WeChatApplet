package com.zw.api.controller;

import com.zw.common.utils.JsonResult;
import com.zw.common.utils.UploadFile;
import com.zw.common.utils.annotation.Authorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/file")
public class FIleController {
    @Autowired
    UploadFile uploadFile;

    //上传文件
    @Authorize
    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file, HttpServletRequest request){
        String openid = request.getAttribute("openid").toString();
        String path ="/"+openid.substring(0,9);
        String url = uploadFile.saveFile(file,path);
        return url;
    }

    //删除文件
    @Authorize
    @PostMapping("/delete")
    public JsonResult<Boolean> delete(@RequestBody String url){
        // 将url从file分割出来
        String filePath = url.split("file/")[1];
        return new JsonResult<>(200,uploadFile.deleteFile(filePath));
    }

}
