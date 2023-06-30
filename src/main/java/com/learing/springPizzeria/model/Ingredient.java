package com.learing.springPizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @NotNull
  @Size(min = 2, max = 80)
  @Column(nullable = false)
  private String ingredient;
  
  @ManyToMany(mappedBy = "ingredients")
  private List<Pizza> pizze = new ArrayList<>();
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getIngredient() {
    return ingredient;
  }
  
  public void setIngredient(String ingredient) {
    this.ingredient = ingredient;
  }
  
  public List<Pizza> getPizze() {
    return pizze;
  }
  
  public void setBooks(List<Pizza> pizze) {
    this.pizze = pizze;
  }
}