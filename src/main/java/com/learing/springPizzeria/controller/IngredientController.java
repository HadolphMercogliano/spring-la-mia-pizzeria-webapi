package com.learing.springPizzeria.controller;

import com.learing.springPizzeria.model.Ingredient;
import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.IngredientRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
  
  @Autowired
  private IngredientRepo ingredientRepo;
  
  @GetMapping
  public String index(Model model, @RequestParam("edit")Optional<Integer> ingredientId) {
    List<Ingredient> ingredientList = ingredientRepo.findAll();
    model.addAttribute("ingredients", ingredientList);
    
    Ingredient ingredientObj;
    if (ingredientId.isPresent()) {
      Optional<Ingredient> ingredientDB = ingredientRepo.findById(ingredientId.get());
      if (ingredientDB.isPresent()) {
        ingredientObj = ingredientDB.get();
      } else {
        ingredientObj = new Ingredient();
      }
    } else {
      ingredientObj = new Ingredient();
    }
    model.addAttribute("ingredientObj", ingredientObj);
    return "/ingredients/index";
  }
  
  @PostMapping("/save")
  public String save(@Valid @ModelAttribute("ingredientObj") Ingredient formIngredient, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("ingredients", ingredientRepo.findAll());
      return "/ingredients/index";
    }
    ingredientRepo.save(formIngredient);
    return "redirect:/ingredients";
  }
  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Integer id) {
    // prima di eliminare l'ingrediente bisogna dissociarlo dalle pizze.
    Optional<Ingredient> result = ingredientRepo.findById(id);
    if (result.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    Ingredient ingredientToDelete = result.get();
    for (Pizza pizza : ingredientToDelete.getPizze()) {
      pizza.getIngredients().remove(ingredientToDelete);
    }
    ingredientRepo.deleteById(id);
    return "redirect:/ingredients";
  }
}