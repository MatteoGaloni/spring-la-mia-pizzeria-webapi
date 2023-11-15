package com.experise.course.springpizza.controller;

import com.experise.course.springpizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/promotions")
public class PromotionController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/create")
    public String create() {

        return "";
    }
}
