package com.learing.springPizzeria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pizze")
public class Pizza {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(nullable = false, unique = true)
  private String name;
  
  private String description;
  private String imageUrl;
  private BigDecimal price;
  
  
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
}