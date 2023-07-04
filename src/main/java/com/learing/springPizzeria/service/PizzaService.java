package com.learing.springPizzeria.service;

import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
  
  @Autowired
  PizzaRepo pizzaRepo;
  
  public List<Pizza> getAll(Optional<String> keywordOpt) {
    if (keywordOpt.isEmpty()) {
      return pizzaRepo.findAll();
    }else {
      return pizzaRepo.findByNameContainingIgnoreCase(keywordOpt.get());
    }
    
  }
  
}
