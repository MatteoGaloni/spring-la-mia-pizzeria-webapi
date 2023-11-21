package com.experise.course.springpizza.service;

import com.experise.course.springpizza.exceptions.PizzaNotFoundException;
import com.experise.course.springpizza.model.Pizza;
import com.experise.course.springpizza.repository.IngredientRepository;
import com.experise.course.springpizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Pizza> getPizzaList(String search) {
        if (search != null && !search.isBlank()) {
            return pizzaRepository.findByNameContainsAllIgnoreCase(search);
        } else {
            return pizzaRepository.findAll();
        }
    }

    public Page<Pizza> getPizzaList(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return pizzaRepository.findByNameContainsAllIgnoreCase(search, pageable);
        } else {
            return pizzaRepository.findAll(pageable);
        }
    }


    public List<Pizza> advancedSearch(String name, String description, BigDecimal price) {
        if (name != null && name.isBlank()) {
            name = null;
        }
        if (description != null && description.isBlank()) {
            description = null;
        }
        return pizzaRepository.findByNameContainsOrDescriptionContainsOrPriceLessThan(name, description, price);
    }

    public Pizza getPizzaById(Integer id) throws PizzaNotFoundException {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException("Pizza with id " + id + " not found");
        }
    }

    public Pizza createPizza(Pizza pizza) {
        pizza.setId(null);
        return pizzaRepository.save(pizza);
    }

    public Pizza editPizza(Pizza pizza) throws PizzaNotFoundException {
        Optional<Pizza> result = pizzaRepository.findById(pizza.getId());
        if (result.isPresent()) {
            pizza.setCreatedAt(result.get().getCreatedAt());
            return pizzaRepository.save(pizza);
        } else {
            throw new PizzaNotFoundException("Id not found");
        }
    }

    public void deletePizza(Pizza pizza) {
        pizzaRepository.deleteById(pizza.getId());
    }

}
