package com.gialinh.shop.service.custom;

import com.gialinh.shop.domain.Category;
import com.gialinh.shop.repository.CategoryRepository;
import com.gialinh.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
public class CategoryServiceCustom {
    @Autowired
    private CategoryRepository categoryRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(CategoryService.class.getSimpleName());

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> categories(){
        return categoryRepository.findAllByStatus(1);
    }

    public List<Category> findAllNoStatus (){
        return categoryRepository.findAll();
    }
}
