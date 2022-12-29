package com.zw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zw.common.domain.Batch;
import com.zw.mapper.BatchMapper;
import com.zw.service.BatchService;
import org.springframework.stereotype.Service;

/**
* @author aking
* @description 针对表【mall_batch】的数据库操作Service实现
* @createDate 2022-12-10 18:31:52
*/
@Service
public class BatchServiceImpl extends ServiceImpl<BatchMapper, Batch>
    implements BatchService {

}




