package com.learing.springPizzeria.api;

import com.learing.springPizzeria.model.Pizza;
import com.learing.springPizzeria.repository.PizzaRepo;
import com.learing.springPizzeria.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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
    Optional<Pizza> pizza = pizzaRepo.findById(id);
    if( pizza.isPresent()) {
      return pizza.get();
    } else {
    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }
  
  @PostMapping
  public Pizza create(@Valid @RequestBody Pizza pizza, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
    }
    return pizzaRepo.save(pizza);
  }
  
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    pizzaRepo.deleteById(id);
  }
  
  @PutMapping("/{id}")
  public Pizza update(@PathVariable Integer id,@Valid @RequestBody Pizza pizza) {
    pizza.setId(id);
    return pizzaRepo.save(pizza);
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
