package com.zw.backstage.controller;

import com.zw.common.domain.Batch;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchController {
    @Autowired
    private BatchService batchService;

    // 获取批次列表
    @Authorize
    @GetMapping("/list")
    public JsonResult<List<Batch>> getAllBatch(){
        return new JsonResult(200,batchService.list());
    }

    // 根据id删除批次
    @Authorize
    @DeleteMapping("/delete/{id}")
    public JsonResult<Void> delete(@PathVariable("id") Integer id){
        batchService.removeById(id);
        return new JsonResult<>(200);
    }

    // 根据id修改批次
    @Authorize
    @PutMapping("/update")
    public JsonResult<Void> update(@RequestBody Batch batch){
        batchService.updateById(batch);
        return new JsonResult<>(200);
    }

    // 添加批次
    @Authorize
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestBody Batch batch){
        batchService.save(batch);
        return new JsonResult<>(200);
    }
}
