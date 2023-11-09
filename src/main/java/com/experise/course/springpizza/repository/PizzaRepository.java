package com.experise.course.springpizza.repository;

import com.experise.course.springpizza.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
