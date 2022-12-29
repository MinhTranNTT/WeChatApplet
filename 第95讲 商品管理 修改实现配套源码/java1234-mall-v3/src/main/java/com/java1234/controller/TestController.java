package com.java1234.controller;

import com.java1234.entity.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-23 11:54
 */
@RestController
@RequestMapping("/java1234")
public class TestController {

    /**
     * 测试
     * @return
     */
    @GetMapping("/test")
    public R test(){
        return R.ok("流弊java1234");
    }
}
