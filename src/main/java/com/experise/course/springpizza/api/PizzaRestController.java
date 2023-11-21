package com.experise.course.springpizza.api;

import com.experise.course.springpizza.exceptions.PizzaNotFoundException;
import com.experise.course.springpizza.model.Pizza;
import com.experise.course.springpizza.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pizzas")
@CrossOrigin
public class PizzaRestController {
    @Autowired
    PizzaService pizzaService;

    @GetMapping
    public Page<Pizza> index(@PageableDefault(page = 0, size = 4) Pageable pageable, @RequestParam(value = "search", required = false) String search) {
        return pizzaService.getPizzaList(search, pageable);
    }

    @GetMapping("/{id}")
    public Pizza show(@PathVariable Integer id) {
        try {
            return pizzaService.getPizzaById(id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {
        return pizzaService.createPizza(pizza);
    }

    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {
        pizza.setId(id);
        try {
            return pizzaService.editPizza(pizza);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        try {
            pizzaService.deletePizza(pizzaService.getPizzaById(id));
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
