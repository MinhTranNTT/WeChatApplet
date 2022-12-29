package com.zw.api.controller;

import com.zw.common.domain.Camper;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.UploadFile;
import com.zw.common.utils.annotation.Authorize;
import com.zw.mapper.CamperMapper;
import com.zw.service.CamperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user/camper")
public class CamperController {
    @Autowired
    CamperService camperService;
    @Autowired
    CamperMapper camperMapper;
    @Autowired
    UploadFile uploadFile;

    //获取该用户所有的营员
    @Authorize
    @GetMapping("/list")
    public JsonResult<List<Camper>> getCamperList(HttpServletRequest request){
        String openid = request.getAttribute("openid").toString();
        return new JsonResult<>(200,camperMapper.getAllByOpenid(openid));
    }

    //存入营员信息
    @Authorize
    @PostMapping("/save")
    public JsonResult<Boolean> saveCamper(@RequestBody Camper camper, HttpServletRequest request){
        String openid = request.getAttribute("openid").toString();
        camper.setOpenid(openid);
        return new JsonResult<>(200,camperService.save(camper));
    }

    //修改营员信息
    @Authorize
    @PutMapping("/update")
    public JsonResult<Boolean> updateCamper(@RequestBody Camper camper, HttpServletRequest request){
        String openid = request.getAttribute("openid").toString();
        camper.setOpenid(openid);
        return new JsonResult<>(200,camperService.updateById(camper));
    }

    //删除营员信息
    @Authorize
    @DeleteMapping("/delete/{id}")
    public JsonResult<Boolean> deleteCamper(@PathVariable Integer id, HttpServletRequest request){
        Camper camper = camperService.getById(id);
        String photoUrl = camper.getPhotoUrl();
        String savedPath = photoUrl.split("file/")[1];
        uploadFile.deleteFile(savedPath);
        return new JsonResult<>(200,camperService.removeById(id));
    }
}
