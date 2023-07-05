package com.learing.springPizzeria.service;

import com.learing.springPizzeria.dto.PizzaForm;
import com.learing.springPizzeria.exeptions.NotUniqueNameExeption;
import com.learing.springPizzeria.exeptions.PizzaNotFound;
import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    pizzaToPersist.setIngredients(pizza.getIngredients());
    pizzaToPersist.setCover(pizza.getCover());
    return pizzaRepo.save(pizzaToPersist);
  }
  
//  metodo per creare una pizza partendo dalla classe PizzaForm
  public Pizza create(PizzaForm pizzaForm){
    Pizza pizza = mapPizzaFormToPizza(pizzaForm);
    return create(pizza);
  }
  
  public PizzaForm getPizzaFormById(Integer id) throws PizzaNotFound{
    Pizza pizza = getById(id);
    return mapPizzaToPizzaForm(pizza);
  }
  
  public Pizza update(PizzaForm pizzaForm) throws PizzaNotFound, NotUniqueNameExeption {
    Pizza pizza = mapPizzaFormToPizza(pizzaForm);
    Pizza pizzaDb = getById(pizza.getId());
    if (!pizza.getName().equals(pizzaDb.getName()) && !isUniqueName(pizza)) {
      throw  new NotUniqueNameExeption(pizzaDb.getName());
    }
    pizzaDb.setName(pizza.getName());
    pizzaDb.setDescription(pizza.getDescription());
    pizzaDb.setPrice(pizza.getPrice());
    pizzaDb.setIngredients(pizza.getIngredients());
    pizzaDb.setCover(pizza.getCover());
    
    return pizzaRepo.save(pizzaDb);
  }
  
  private Pizza mapPizzaFormToPizza(PizzaForm pizzaForm){
    Pizza pizza = new Pizza();
    
    pizza.setId(pizzaForm.getId());
    pizza.setName(pizzaForm.getName());
    pizza.setDescription(pizzaForm.getDescription());
    pizza.setPrice(pizzaForm.getPrice());
    pizza.setIngredients(pizzaForm.getIngredients());
    
    byte[] coverBytes = multipartFileToByteArray(pizzaForm.getCoverFile());
    pizza.setCover(coverBytes);
    
    return pizza;
  }
  
  private PizzaForm mapPizzaToPizzaForm(Pizza pizza){
    PizzaForm pizzaForm = new PizzaForm();
    
    pizzaForm.setId(pizza.getId());
    pizzaForm.setName(pizza.getName());
    pizzaForm.setDescription(pizza.getDescription());
    pizzaForm.setPrice(pizza.getPrice());
    pizzaForm.setIngredients(pizza.getIngredients());
    
    return pizzaForm;
  }
  
  private byte[] multipartFileToByteArray(MultipartFile mpf){
    byte[] bytes = null;
    if (mpf != null && !mpf.isEmpty()){
    try {
      bytes = mpf.getBytes();
    } catch(IOException e){
      e.printStackTrace();
      }
    }
    return bytes;
  }
  
  private boolean isUniqueName(Pizza formPizza) {
    Optional<Pizza> result = pizzaRepo.findByName(formPizza.getName());
    return result.isEmpty();
  }
  
}
