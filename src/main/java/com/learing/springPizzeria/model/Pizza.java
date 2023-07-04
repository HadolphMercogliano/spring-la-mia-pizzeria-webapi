package com.learing.springPizzeria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizze")
public class Pizza {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @NotBlank(message = "Il nome è obbligatorio")
  @Size(max = 255, message = "Il  nome deve essere di massimo 255 caratteri")
  @Column(nullable = false, unique = true)
  private String name;
  
  @Size(max = 255, message = "La descrizione deve essere di massimo 255 caratteri")
  @NotBlank(message = "La descrizione è obbligatoria e deve essere di massimo 255 caratteri")
  private String description;
  
  @NotBlank(message = "L'immagine è obbligatoria")
  private String imageUrl;
  
  @DecimalMin(value ="0.0", inclusive= false,message ="Il prezzo deve essere maggiore di 0")
  @NotNull (message= "Il prezzo è obbligatorio")
  @Column(nullable = false)
  private BigDecimal price;
  
  @JsonIgnore
  @OneToMany(mappedBy = "pizza", cascade = {CascadeType.REMOVE})
  private List<Discount> discountList = new ArrayList<>(); //Relazione con i discount
  
  @ManyToMany
  @JoinTable(name= "pizza_ingredient",
    joinColumns = @JoinColumn(name = "pizza_id"),
    inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
  private List<Ingredient> ingredients = new ArrayList<>();
  
  public Integer getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public String getImageUrl() {
    return imageUrl;
  }
  
  public BigDecimal getPrice() {
    return price;
  }
  
  public List<Discount> getDiscountList() {
    return discountList;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
  
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
  
  public void setDiscountList(List<Discount> discountList) { this.discountList = discountList;  }
  public List<Ingredient> getIngredients() { return ingredients; }
  
  public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }
}
