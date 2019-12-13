package com.gialinh.shop.web.controller;

import com.gialinh.shop.service.custom.CategoryServiceCustom;
import com.gialinh.shop.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HomeController {
    @Autowired
    CategoryServiceCustom categoryService;

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @GetMapping(value = "/products")
    public String products(Model model) {
        model.addAttribute("categories", categoryService.categories());
        return "products";
    }
}
