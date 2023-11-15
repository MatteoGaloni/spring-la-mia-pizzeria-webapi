package com.experise.course.springpizza.repository;

import com.experise.course.springpizza.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    

}
