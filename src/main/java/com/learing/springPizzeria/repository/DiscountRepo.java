package com.learing.springPizzeria.repository;

import com.learing.springPizzeria.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepo extends JpaRepository<Discount, Integer> {

}
