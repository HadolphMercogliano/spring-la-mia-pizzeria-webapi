package com.learing.springPizzeria.controller;

import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.PizzaRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
  @GetMapping("/create")
  public String create(Model model) {
    model.addAttribute("pizza",new Pizza());
    return "create";
  }
  @PostMapping("/create")
  public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model){
    if(bindingResult.hasErrors()) {
      return "create";
    }
//    formPizza.setCreatedAt(LocalDateTime.now());
    pizzaRepo.save(formPizza);
    return "redirect:/";
  }
//  private boolean isUniqueName(Pizza formPizza){
//    Optional<Pizza> result = PizzaRepo.findByName(formPizza.getName());
//    return result.isEmpty();
//  }
}



