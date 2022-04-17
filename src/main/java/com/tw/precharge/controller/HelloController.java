package com.tw.precharge.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lexu
 */
@RestController
@RequestMapping(value ="/hello")
@Api(tags = "测试接口")
public class HelloController {
    @GetMapping(value ="/test")
    public String test(){
        return "hello world";
    }
}
