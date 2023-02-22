package com.zw.common.utils;


import com.zw.common.utils.ex.FileEmptyException;
import com.zw.common.utils.ex.FileSizeException;
import com.zw.common.utils.ex.FileStateException;
import com.zw.common.utils.ex.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 主要的上传图片和视频的类。
 */
@Slf4j
@Component
public class UploadFile {

    /**
     * 文件绑定的路径
     */
    @Value("${file.root_dir}")
    private String fi_p;

    @Value("${file.host_path}")
    private String hostPath;

    public String getImagePath() {
        return imagePath;
    }

    @Value("${file.image_path}")
    private String imagePath;

    public String getFi_p() {
        return fi_p;
    }

    public UploadFile(){
    }

    /* 图片文件大小的上限值（10MB）*/
    public static final int IMAGE_MAX_SIZE = 10 * 1024 *1024;

    /* 视频文件大小的上限值（100MB）*/
    public static final int VIDEO_MAX_SIZE = 50 *1024*1024;

    /* 允许上传的图片文件的类型 */
    public static final List<String> IMAGE_TYPES = new ArrayList<>();
    public static final List<String> VIDEO_TYPES = new ArrayList<>();

    /* 初始化允许上传的图片文件类型*/
    static {
        // 图片文件类型
        IMAGE_TYPES.add("image/jpeg");
        IMAGE_TYPES.add("image/png");
        IMAGE_TYPES.add("image/bmp");
        IMAGE_TYPES.add("image/gif");
    }
    /* 允许上传的视频文件类型*/
    static {
        VIDEO_TYPES.add("video/mp4");
        VIDEO_TYPES.add("video/avi");
        VIDEO_TYPES.add("audio/mp3");
        VIDEO_TYPES.add("video/x-ms-wmv");
    }

    public String uploadImage111(MultipartFile file,String filePath){
        return saveFile(file,filePath);
    }


    public String saveFile(MultipartFile file,String Path){
        // 判断上传的文件是否为空
        if (file.isEmpty()){
            throw new FileEmptyException("上传的头像文件不允许为空");
        }

        // 判断上传的视频文件大小是否超出限制值
        if (file.getSize() > VIDEO_MAX_SIZE && VIDEO_TYPES.contains(file.getContentType())){
            throw new FileSizeException("不允许上传超过"+(VIDEO_MAX_SIZE / 1024) +"KB的视频文件");
        }
        // 判断上传的图片文件大小是否超出限制值
        if (file.getSize() > IMAGE_MAX_SIZE && IMAGE_TYPES.contains(file.getContentType())){
            throw new FileSizeException("不允许上传超过"+(IMAGE_MAX_SIZE / 1024)+"KB的图片文件");
        }

        // 获取当前项目的绝对磁盘路径
//        String parent = session.getServletContext().getRealPath("upload");


//        String parent = "C:\\Users\\aking\\Desktop\\image/";
        String parent = this.fi_p;
//        FileUpload fu = new FileUpload();
//        String parent =fu.getFi_p();
//        System.out.println(parent+Path);
        // 保存图片的文件夹
        File dir = new File(parent+Path);
        if (!dir.exists()){
            dir.mkdirs();
        }

        // 保存的头像文件的文件名
        String suffix = "";
        String originalFileName = file.getOriginalFilename();
        int beginIndex = originalFileName.lastIndexOf(".");
        if (beginIndex > 0 ){
            suffix = originalFileName.substring(beginIndex);
        }
        String fileName = UUID.randomUUID().toString()+suffix;

        // 创建文件对象，表示保存的头像文件
        File dest = new File(dir,fileName);

        //执行保存图片文件
        try{
            file.transferTo(dest);
        }catch (IllegalStateException e){
            throw new FileStateException("文件状态异常，可能文件已被移动或删除");
        }catch (IOException e){
            throw new FileUploadException("上传文件时读写错误，请稍后重新尝试");
        }

        // 图片文件路径
//        String image_path = "/upload/"+fileName;
        // 从 Session 中获取 uid 和 username
        String image_path = this.hostPath + Path +"/"+fileName;
//        Integer uid = getUidFromSession
//        System.out.println(session);

        return image_path;
    }


    public Boolean deleteFile(String filePath){
        String path = this.fi_p + filePath;
        try {
            File file = new File(path);
            if(file.delete()){
                return true;
            }else {
                log.info("文件不存在");
                return false;
            }
        }catch (Exception e){
            log.error("文件操作失败");
        }
        return true;
    }

}
