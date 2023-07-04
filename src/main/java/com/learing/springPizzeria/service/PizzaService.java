package com.learing.springPizzeria.service;

import com.learing.springPizzeria.exeptions.NotUniqueNameExeption;
import com.learing.springPizzeria.exeptions.PizzaNotFound;
import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
// service è un estensione di Bean
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
  public Pizza getById(Integer id) throws PizzaNotFound {
    Optional<Pizza> pizzaOpt = pizzaRepo.findById(id);
    if (pizzaOpt.isPresent()) {
      return pizzaOpt.get();
    } else {
      throw new PizzaNotFound("La pizza con id " + id + " non è stata trovata");
    }
  }
  public Pizza create(Pizza pizza) throws NotUniqueNameExeption{
    if (!isUniqueName(pizza)) {
      throw new NotUniqueNameExeption(pizza.getName());
    }
    Pizza pizzaToPersist = new Pizza();
    pizzaToPersist.setName(pizza.getName());
    pizzaToPersist.setDescription(pizza.getDescription());
    pizzaToPersist.setPrice(pizza.getPrice());
    pizzaToPersist.setImageUrl(pizza.getImageUrl());
    pizzaToPersist.setIngredients(pizza.getIngredients());
    return pizzaRepo.save(pizzaToPersist);
  }
  
  private boolean isUniqueName(Pizza formPizza) {
    Optional<Pizza> result = pizzaRepo.findByName(formPizza.getName());
    return result.isEmpty();
  }
  
}
