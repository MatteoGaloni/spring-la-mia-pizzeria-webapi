package com.experise.course.springpizza.repository;

import com.experise.course.springpizza.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    List<Pizza> findByNameContainsAllIgnoreCase(String name);

    List<Pizza> findByNameContainsOrDescriptionContainsOrPriceLessThan(String name, String description, BigDecimal price);
}
