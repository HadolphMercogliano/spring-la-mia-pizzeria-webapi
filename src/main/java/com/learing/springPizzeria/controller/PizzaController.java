package com.learing.springPizzeria.controller;

import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
  
  @Autowired
  private PizzaRepo pizzaRepo;
  
  @GetMapping
  public String index(
    @RequestParam(name= "keyword", required=false) String searchString,
    Model model) {
    List<Pizza> pizze;
    if (searchString == null || searchString.isBlank()) {
      pizze = pizzaRepo.findAll();
    }else {
      pizze = pizzaRepo.findByNameContainingIgnoreCase(searchString);
    }
    model.addAttribute("searchInput", searchString == null ? "" :searchString);
      model.addAttribute("pizze", pizze);
      return "index";
    }
  
  @GetMapping("/{id}")
  public String details(@PathVariable("id") Integer id, Model model) {
    
    Optional<Pizza> result = pizzaRepo.findById(id);
    if(result.isPresent()) {
    model.addAttribute("pizza", result.get());
    return "details";
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza non trovata");
    }
  }
}



