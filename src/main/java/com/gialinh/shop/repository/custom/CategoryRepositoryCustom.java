package com.gialinh.shop.repository.custom;

import com.gialinh.shop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepositoryCustom extends JpaRepository<Category, Long> {
    List<Category> findAllByStatus(int status);
}
