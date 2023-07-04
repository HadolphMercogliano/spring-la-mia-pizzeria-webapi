package com.learing.springPizzeria.api;

import com.learing.springPizzeria.exeptions.NotUniqueNameExeption;
import com.learing.springPizzeria.exeptions.PizzaNotFound;
import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.PizzaRepo;
import com.learing.springPizzeria.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/pizza")
public class PizzaRestController {
  
  
  @Autowired
  private PizzaRepo pizzaRepo;
  
  @Autowired
  private PizzaService pizzaService;
  @GetMapping
  public List<Pizza> index(@RequestParam Optional<String> keyword) {
    return pizzaService.getAll(keyword);
  }

  @GetMapping("/{id}")
  public Pizza get(@PathVariable Integer id) {
    try {
      return pizzaService.getById(id);
    } catch (PizzaNotFound e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
  
  @PostMapping
  public Pizza create(@Valid @RequestBody Pizza pizza) {
    try {
      return pizzaService.create(pizza);
    } catch (NotUniqueNameExeption e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }
  
  //  UPDATE METHOD
  @PutMapping("/{id}")
  public Pizza update(@PathVariable Integer id,@Valid @RequestBody Pizza pizza) {
    pizza.setId(id);
    return pizzaRepo.save(pizza);
  }
//  DELETE METHOD
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    pizzaRepo.deleteById(id);
  }
  
  //Paging
  @GetMapping("/page")
  public Page<Pizza> page(
//    @RequestParam(defaultValue = "3") Integer size,
//    @RequestParam(defaultValue = "0") Integer page
    Pageable pageable
  ) {
//    Pageable pageable = PageRequest.of(page,size);
    return pizzaRepo.findAll(pageable);
  }
}
