package com.experise.course.springpizza.service;

import com.experise.course.springpizza.model.Pizza;
import com.experise.course.springpizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> getPizzaList(String search) {
        if (search != null && !search.isBlank()) {
            return pizzaRepository.findByNameContainsAllIgnoreCase(search);
        } else {
            return pizzaRepository.findAll();
        }
    }

    public Pizza getPizzaById(Integer id) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new IllegalArgumentException("Pizza with id" + id + "not found");
        }
    }

    public Pizza createPizza(Pizza pizza) {
        try {
            return pizzaRepository.save(pizza);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }
}
