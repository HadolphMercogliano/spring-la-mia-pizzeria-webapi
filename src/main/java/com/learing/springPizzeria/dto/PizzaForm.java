package com.learing.springPizzeria.dto;

import com.learing.springPizzeria.model.Ingredient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PizzaForm {
  
  private Integer id;
  
  @NotBlank(message = "Il nome è obbligatorio")
  @Size(max = 255, message = "Il  nome deve essere di massimo 255 caratteri")
  private String name;
  
  @Size(max = 255, message = "La descrizione deve essere di massimo 255 caratteri")
  @NotBlank(message = "La descrizione è obbligatoria e deve essere di massimo 255 caratteri")
  private String description;
  
  @DecimalMin(value ="0.0", inclusive= false,message ="Il prezzo deve essere maggiore di 0")
  @NotNull(message= "Il prezzo è obbligatorio")
  private BigDecimal price;
  
  private MultipartFile coverFile;
 
  private List<Ingredient> ingredients = new ArrayList<>();
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public MultipartFile getCoverFile() {
    return coverFile;
  }
  
  public void setCoverFile(MultipartFile coverFile) {
    this.coverFile = coverFile;
  }
  
  public BigDecimal getPrice() {
    return price;
  }
  
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
  
  public List<Ingredient> getIngredients() {
    return ingredients;
  }
  
  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }
}
