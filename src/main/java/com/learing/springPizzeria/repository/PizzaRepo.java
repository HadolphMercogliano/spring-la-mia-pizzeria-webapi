package com.learing.springPizzeria.repository;

import com.learing.springPizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PizzaRepo extends JpaRepository<Pizza, Integer> {
//  List<Pizza> findByName(String name);

  List<Pizza> findByNameContainingIgnoreCase(String name);
  
//  custom query
//  @Query("")
}