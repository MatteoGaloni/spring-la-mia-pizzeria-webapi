package com.experise.course.springpizza.api;

import com.experise.course.springpizza.model.Pizza;
import com.experise.course.springpizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
@CrossOrigin
public class PizzaRestController {
    @Autowired
    PizzaService pizzaService;

    @GetMapping
    public List<Pizza> index(@RequestParam(value = "search", required = false) String search) {
        return pizzaService.getPizzaList(search);
    }
}
