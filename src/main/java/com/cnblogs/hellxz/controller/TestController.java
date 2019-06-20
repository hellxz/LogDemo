package com.cnblogs.hellxz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller
 * @author hellxz
 */
@RestController
@RequestMapping("test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/{args}")
    public String test(@PathVariable String args){
        logger.info("测试通过，你输入的是{}，当前时间为：{}", args, System.currentTimeMillis());
        return "调用成功，你输入的是" + args;
    }
}
