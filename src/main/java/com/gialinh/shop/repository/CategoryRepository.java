package com.gialinh.shop.repository;
import com.gialinh.shop.domain.Category;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByStatus(int status);
}
