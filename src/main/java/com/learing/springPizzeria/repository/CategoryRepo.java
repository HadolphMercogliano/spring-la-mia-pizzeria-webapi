package com.learing.springPizzeria.repository;

import com.learing.springPizzeria.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}