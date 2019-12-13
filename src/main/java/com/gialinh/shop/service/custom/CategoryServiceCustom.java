package com.gialinh.shop.service.custom;

import com.gialinh.shop.domain.Category;
import com.gialinh.shop.repository.custom.CategoryRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceCustom {
    @Autowired
    CategoryRepositoryCustom categoryRepository;

    public List<Category> categories (){
        return categoryRepository.findAllByStatus(1);
    }
}
