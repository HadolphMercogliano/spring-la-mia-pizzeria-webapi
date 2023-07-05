package com.learing.springPizzeria.controller;

import com.learing.springPizzeria.dto.PizzaForm;
import com.learing.springPizzeria.exeptions.NotUniqueNameExeption;
import com.learing.springPizzeria.exeptions.PizzaNotFound;
import com.learing.springPizzeria.messages.AlertMessage;
import com.learing.springPizzeria.messages.AlertMessageType;
import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.IngredientRepo;
import com.learing.springPizzeria.repository.PizzaRepo;
import com.learing.springPizzeria.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
  
  @Autowired
  private PizzaRepo pizzaRepo;
  @Autowired
  private IngredientRepo ingredientRepo;
  @Autowired
  PizzaService pizzaService;
  
  @GetMapping
  public String index(
    @RequestParam(name= "keyword", required=false) String searchString, Authentication authentication,
    Model model) {
    List<Pizza> pizze;
    if (searchString == null || searchString.isBlank()) {
      pizze = pizzaRepo.findAll();
    }else {
      pizze = pizzaRepo.findByNameContainingIgnoreCase(searchString);
    }
    model.addAttribute("searchInput", searchString == null ? "" :searchString);
      model.addAttribute("pizze", pizze);
    model.addAttribute("username", authentication.getName());
      return "index";
    }
  
  @GetMapping("/{id}")
  public String details(@PathVariable("id") Integer id, Model model) {
   Pizza pizza = getPizzaById(id);
    model.addAttribute("pizza", pizza);
    return "details";
    
  }
  
//  chiede la view per aggiungere una nuova pizza
  @GetMapping("/create")
  public String create(Model model) {
    model.addAttribute("pizza",new PizzaForm());
    model.addAttribute("ingredientList", ingredientRepo.findAll());
    
    return "edit";
  }
//  invia al db e salva la nuova pizza
  @PostMapping("/create")
  public String store(@Valid @ModelAttribute("pizza") PizzaForm formPizza, BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model) {
    if (!bindingResult.hasErrors()) {
      try {
      pizzaService.create(formPizza);
      } catch (NotUniqueNameExeption e) {
        bindingResult.addError(new FieldError("pizza", "name",formPizza.getName(),false,null,null, "il nome deve essere unico" ));
      }
    }
    if(bindingResult.hasErrors()) {
      model.addAttribute("ingredientList", ingredientRepo.findAll());
      return "edit";
    }
//    pizzaRepo.save(formPizza);
    redirectAttributes.addFlashAttribute("message",
      new AlertMessage(AlertMessageType.SUCCESS, "Pizza creata!"));
    return "redirect:/pizza";
  }
  
//  chiedi la view del modulo per modificare la pizza
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Integer id, Model model) {
    try {
      PizzaForm pizzaForm = pizzaService.getPizzaFormById(id);
      model.addAttribute("pizza", pizzaForm);
      model.addAttribute("ingredientList", ingredientRepo.findAll());
      return "edit";
    } catch (PizzaNotFound e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }
  
//  invia e salva la modifica della pizza
  @PostMapping("/edit/{id}")
  public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") PizzaForm formPizza, BindingResult bindingResult, RedirectAttributes redirectAttributes,Model model) {
//    Pizza pizzaToEdit = getPizzaById(id); //vecchia versione pizza (formPizza è la nuova)
    
    if (!bindingResult.hasErrors()) {
      try {
      pizzaService.update(formPizza);
      } catch (PizzaNotFound e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      } catch (NotUniqueNameExeption e) {
        bindingResult.addError(new FieldError("pizza","name", formPizza.getName(),false,null,null, "Il nome deve essere unico" ));
      }
    }
    if (bindingResult.hasErrors()) {
      model.addAttribute("ingredientList", ingredientRepo.findAll());
    return "edit";
    }
    
    redirectAttributes.addFlashAttribute("message",
      new AlertMessage(AlertMessageType.SUCCESS, "Pizza modificata con successo!"));
    return "redirect:/pizza";
  }
  
//  cancella la pizza
  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    Pizza pizzaToDelete=getPizzaById(id);
    pizzaRepo.delete(pizzaToDelete);
    redirectAttributes.addFlashAttribute("message",new AlertMessage(AlertMessageType.SUCCESS, "pizza " + pizzaToDelete.getName() + " eliminata"));
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



