package com.experise.course.springpizza.controller;

import com.experise.course.springpizza.exceptions.PizzaNotFoundException;
import com.experise.course.springpizza.model.Pizza;
import com.experise.course.springpizza.service.IngredientService;
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

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;

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
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/advancedSearch")
    public String advancedSearch(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) BigDecimal price,
            Model model) {

        model.addAttribute("pizzaList", pizzaService.advancedSearch(name, description, price));
        return "pizzas/advancedSearch";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredients", ingredientService.getIngredients());
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    @PostMapping("/store")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza,
                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.getIngredients());
            return "pizzas/create";
        }
        if (formPizza.getImg().isBlank()) {
            formPizza.setImg("https://www.emme2servizi.it/wp-content/uploads/2020/12/no-image.jpg");
        }
        pizzaService.createPizza(formPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {
            Pizza pizza = pizzaService.getPizzaById(id);
            model.addAttribute("ingredients", ingredientService.getIngredients());
            model.addAttribute("pizza", pizzaService.getPizzaById(id));
            return "pizzas/edit";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.getIngredients());
            return "pizzas/edit";
        }
        if (formPizza.getImg().isBlank()) {
            formPizza.setImg("https://www.emme2servizi.it/wp-content/uploads/2020/12/no-image.jpg");
        }
        try {
            pizzaService.editPizza(formPizza);
            return "redirect:/pizzas/show/" + formPizza.getId();
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Pizza pizza = null;
        try {
            pizza = pizzaService.getPizzaById(id);
            pizzaService.deletePizza(pizza);
            return "redirect:/pizzas";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
