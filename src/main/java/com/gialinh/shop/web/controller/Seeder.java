package com.gialinh.shop.web.controller;

import com.google.common.collect.Range;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping(value = "/seeder")
public class Seeder {
    Random random = new Random();
    public String seeder (){
        return "OK";
    }
}
