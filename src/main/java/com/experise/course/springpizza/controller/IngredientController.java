package com.experise.course.springpizza.controller;

import com.experise.course.springpizza.model.Ingredient;
import com.experise.course.springpizza.repository.IngredientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredientsList", ingredientRepository.findAll());
        model.addAttribute("ingredient", new Ingredient());
        return "ingredients/list";
    }

    @PostMapping("/store")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient,
                        BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientsList", ingredientRepository.findAll());
            return "ingredients/list";
        }
        redirectAttributes.addFlashAttribute("message", "Ingrediente aggiunto");
        ingredientRepository.save(formIngredient);
        return "redirect:/ingredients";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id" + id));
        ingredientRepository.delete(ingredient);
        return "redirect:/ingredients";
    }
}
