package com.learing.springPizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "discounts")
public class Discount {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @NotNull
  private String discountName;
  @NotNull
  private LocalDate discountStartDate;
  
  private LocalDate discountEndDate;
  
  
  @ManyToOne
  @JoinColumn(name="pizza_id", nullable = false)
  private Pizza pizza; //attributo per relazione con pizza
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getDiscountName() {
    return discountName;
  }
  
  public void setDiscountName(String discountName) {
    this.discountName = discountName;
  }
  
  public LocalDate getDiscountStartDate() {
    return discountStartDate;
  }
  
  public void setDiscountStartDate(LocalDate discountStartDate) {
    this.discountStartDate = discountStartDate;
  }
  
  public LocalDate getDiscountEndDate() {
    return discountEndDate;
  }
  
  public void setDiscountEndDate(LocalDate discountEndDate) {
    this.discountEndDate = discountEndDate;
  }
  
  public Pizza getDiscountPizza() {
    return pizza;
  }
  
  public void setDiscountPizza(Pizza discountPizza) {
    this.pizza = pizza;
  }
}
