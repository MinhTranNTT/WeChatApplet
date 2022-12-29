package com.zw.api.controller;

import com.zw.common.domain.Batch;
import com.zw.common.utils.JsonResult;
import com.zw.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/batch")
@RestController
public class BatchController {
    @Autowired
    BatchService batchService;

    //根据batchid获取batch
    @RequestMapping("/getBatchById/{id}")
    public JsonResult<Batch> getBatchById(@PathVariable Integer id){
        return new JsonResult(200, batchService.getById(id));
    }

    // 根据batchid更新batch信息
    @RequestMapping("/update")
    public JsonResult<Boolean> updateBatch(@PathVariable Batch batch){
        return new JsonResult(200, batchService.updateById(batch));
    }
}
