package com.learing.springPizzeria.controller;

import com.learing.springPizzeria.exeptions.PizzaNotFound;
import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {
  
  @Autowired
  PizzaService pizzaService;
  @GetMapping("/cover/{pizzaId}")
  public ResponseEntity<byte[]> getPizzaCover(@PathVariable Integer pizzaId) {
    try {
      Pizza pizza = pizzaService.getById(pizzaId);
      MediaType mediaType = MediaType.IMAGE_JPEG;
      return ResponseEntity.ok().contentType(mediaType).body(pizza.getCover());
    } catch (PizzaNotFound e){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
