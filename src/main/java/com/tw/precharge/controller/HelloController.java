package com.tw.precharge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lexu
 */
@RestController
@RequestMapping(value ="/hello")
public class HelloController {
    @GetMapping(value ="/test")
    public String test(){
        return "success";
    }
}
