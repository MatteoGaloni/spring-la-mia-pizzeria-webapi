package com.experise.course.springpizza.controller;

import com.experise.course.springpizza.model.Pizza;
import com.experise.course.springpizza.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Pizza> pizzaList;
        if (search != null && !search.isBlank()) {
            pizzaList = pizzaRepository.findByNameContainsAllIgnoreCase(search);
        } else {
            pizzaList = pizzaRepository.findAll();
        }
        model.addAttribute("pizzaList", pizzaList);
        return "pizzas/list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "pizzas/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/advancedSearch")
    public String advancedSearch(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) BigDecimal price,
            Model model) {
        List<Pizza> pizzaList;

        if (name != null && name.isBlank()) {
            name = null;
        }
        if (description != null && description.isBlank()) {
            description = null;
        }
        pizzaList = pizzaRepository.findByNameContainsOrDescriptionContainsOrPriceLessThan(name, description, price);
        model.addAttribute("pizzaList", pizzaList);
        return "pizzas/advancedSearch";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    @PostMapping("/store")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pizzas/create";
        }
        if (formPizza.getImg().isBlank()) {
            formPizza.setImg("https://www.emme2servizi.it/wp-content/uploads/2020/12/no-image.jpg");
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizzas";
    }


//    @GetMapping("/delete/{id}")
//    public String deletePizza(@PathVariable("id") Integer id, Model model) {
//        Pizza pizza = pizzaRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        pizzaRepository.delete(pizza);
//        return "redirect:/pizzas";
//    }
}
