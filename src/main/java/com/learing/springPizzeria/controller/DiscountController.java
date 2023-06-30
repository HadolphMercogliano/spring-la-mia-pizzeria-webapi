package com.learing.springPizzeria.controller;

import com.learing.springPizzeria.model.Discount;
import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.DiscountRepo;
import com.learing.springPizzeria.repository.PizzaRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/discounts")
public class DiscountController {
  @Autowired
  PizzaRepo pizzaRepo;
  
  @Autowired
  DiscountRepo discountRepo;
  
  @GetMapping("/create")
  public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
    Discount discount = new Discount();
    Optional<Pizza> pizza = pizzaRepo.findById(pizzaId);
    if (pizza.isEmpty()){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza con id " + pizzaId + " non trovata");
    }
    discount.setPizza(pizza.get());
    
    model.addAttribute("discount", discount);
    return "/discounts/form";
  }
  
  @PostMapping("/create")
  public String update(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/discounts/form";
    }
    discountRepo.save(formDiscount);
    return "redirect:/pizza/" + formDiscount.getPizza().getId();
  }
  
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Integer id, Model model) {
    
    Optional<Discount> discount = discountRepo.findById(id);
    if (discount.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    model.addAttribute("discount", discount.get());
    return "/discounts/form";
  }
  
  @PostMapping("/edit/{id}")
  public String update(@PathVariable Integer id,
                       @Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult) {
    Optional<Discount> discountToEdit = discountRepo.findById(id);
    
    if (discountToEdit.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    formDiscount.setId(id);

    discountRepo.save(formDiscount);

    return "redirect:/pizza/" + formDiscount.getPizza().getId();
  }
  
  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Integer id) {

    Optional<Discount> discountToDelete = discountRepo.findById(id);
    if (discountToDelete.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    discountRepo.delete(discountToDelete.get());
    return "redirect:/pizza/" + discountToDelete.get().getPizza().getId();
  }
}
