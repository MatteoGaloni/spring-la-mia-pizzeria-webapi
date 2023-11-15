package com.experise.course.springpizza.controller;

import com.experise.course.springpizza.model.Pizza;
import com.experise.course.springpizza.model.Promotion;
import com.experise.course.springpizza.repository.PizzaRepository;
import com.experise.course.springpizza.repository.PromotionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/promotions")
public class PromotionController {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PromotionRepository promotionRepository;

    @GetMapping("/create")
    public String create(@RequestParam Integer pizzaId, Model model) {
        Promotion promotion = new Promotion();
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            promotion.setPizza(result.get());
            model.addAttribute("promotion", promotion);
            return "promotions/create";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/store")
    public String store(@Valid @ModelAttribute("promotion") Promotion formPromotion, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "promotions/create";
        }
        Promotion newPromotion = promotionRepository.save(formPromotion);
        return "redirect:/pizzas/show/" + newPromotion.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Promotion> result = promotionRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("promotion", result.get());
            return "promotions/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("promotion") Promotion formPromotion, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "promotions/edit";
        }
        Optional<Promotion> result = promotionRepository.findById(id);
        if (result.isPresent()) {
            promotionRepository.save(formPromotion);
            return "redirect:/pizzas/show/" + formPromotion.getPizza().getId();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
