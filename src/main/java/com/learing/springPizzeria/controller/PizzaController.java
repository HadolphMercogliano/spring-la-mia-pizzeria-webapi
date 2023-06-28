package com.learing.springPizzeria.controller;

import com.learing.springPizzeria.messages.AlertMessage;
import com.learing.springPizzeria.messages.AlertMessageType;
import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.PizzaRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  
//  chiede la view per aggiungere una nuova pizza
  @GetMapping("/create")
  public String create(Model model) {
    model.addAttribute("pizza",new Pizza());
    return "edit";
  }
//  invia al db e salva la nuova pizza
  @PostMapping("/create")
  public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult,RedirectAttributes redirectAttributes) {
    if (!isUniqueName(formPizza)) {
      // aggiungo a mano un errore nella mappa BindingResult
      bindingResult.addError(new FieldError("pizza", "name", formPizza.getName(), false, null, null,
        "é già stata registrata una pizza con questo nome"));
    }
    if(bindingResult.hasErrors()) {
      return "edit";
    }
    
    pizzaRepo.save(formPizza);
    redirectAttributes.addFlashAttribute("message",
      new AlertMessage(AlertMessageType.SUCCESS, "Pizza creata!"));
    return "redirect:/pizza";
  }
  
//  chiedi la view del modulo per modificare la pizza
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Integer id, Model model) {
    Optional<Pizza> result = pizzaRepo.findById(id);
    if(result.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza con id " + id + " non trovata");
    }
    model.addAttribute("pizza", result.get());
    return "edit";
  }
  
//  invia e salva la modifica della pizza
  @PostMapping("/edit/{id}")
  public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    Pizza pizzaToEdit = getPizzaById(id); //vecchia versione pizza (formPizza è la nuova)
    if (!pizzaToEdit.getName().equals(formPizza.getName()) && !isUniqueName(formPizza)) {
      bindingResult.addError(new FieldError("pizza", "name", formPizza.getName(), false, null, null,
        "Questa pizza esiste già"));
    }
    if (bindingResult.hasErrors()) {
      return "edit";
    }
    formPizza.setId(pizzaToEdit.getId());
    pizzaRepo.save(formPizza);
    redirectAttributes.addFlashAttribute("message",
      new AlertMessage(AlertMessageType.SUCCESS, "Pizza modificata con successo!"));
    return "redirect:/pizza";
  }
  
//  cancella la pizza
  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    Pizza pizzaToDelete=getPizzaById(id);
    pizzaRepo.delete(pizzaToDelete);
    redirectAttributes.addFlashAttribute("message","pizza " + pizzaToDelete.getName() + " eliminata");
    return "redirect:/pizza";
  }
  
  private Pizza getPizzaById(Integer id){
    Optional<Pizza> result = pizzaRepo.findById(id);
    if(result.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza con id " + id + " non trovata");
    }
    return result.get();
  }
  
  private boolean isUniqueName(Pizza formPizza) {
    Optional<Pizza> result = pizzaRepo.findByName(formPizza.getName());
    return result.isEmpty();
  }
  
}



