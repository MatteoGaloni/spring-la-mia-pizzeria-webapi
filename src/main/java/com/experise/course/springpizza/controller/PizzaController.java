package com.experise.course.springpizza.controller;

import com.experise.course.springpizza.model.Pizza;
import com.experise.course.springpizza.repository.IngredientRepository;
import com.experise.course.springpizza.repository.PizzaRepository;
import com.experise.course.springpizza.service.PizzaService;
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
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public String index(@RequestParam(value = "search", required = false) String search, Model model) {
        model.addAttribute("pizzaList", pizzaService.getPizzaList(search));
        return "pizzas/list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        try {
            Pizza pizza = pizzaService.getPizzaById(id);
            model.addAttribute("pizza", pizza);
            return "pizzas/show";
        } catch (Exception e) {
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
        model.addAttribute("ingredients", ingredientRepository.findAll());
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    @PostMapping("/store")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "pizzas/create";
        }
        if (formPizza.getImg().isBlank()) {
            formPizza.setImg("https://www.emme2servizi.it/wp-content/uploads/2020/12/no-image.jpg");
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "pizzas/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pizzas/edit";
        }
        if (formPizza.getImg().isBlank()) {
            formPizza.setImg("https://www.emme2servizi.it/wp-content/uploads/2020/12/no-image.jpg");
        }
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            formPizza.setCreatedAt(result.get().getCreatedAt());
            pizzaRepository.save(formPizza);
            return "redirect:/pizzas/show/" + formPizza.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        pizzaRepository.delete(pizza);
        return "redirect:/pizzas";
    }
}
