package com.LB.Ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.LB.Ecommerce.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
