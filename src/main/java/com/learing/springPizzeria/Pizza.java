package com.learing.springPizzeria;

import java.math.BigDecimal;

public class Pizza {
  private String nome;
  private String descrizione;
  private String image;
  private BigDecimal prezzo;
  
  public String getNome() {
    return nome;
  }
  
  public String getDescrizione() {
    return descrizione;
  }
  
  public String getImage() {
    return image;
  }
  
  public BigDecimal getPrezzo() {
    return prezzo;
  }
  
  public void setNome(String nome) {
    this.nome = nome;
  }
  
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
  
  public void setImage(String image) {
    this.image = image;
  }
  
  public void setPrezzo(BigDecimal prezzo) {
    this.prezzo = prezzo;
  }
}
