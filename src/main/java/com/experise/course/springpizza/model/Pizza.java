package com.experise.course.springpizza.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(max = 30, message = "La lunghezza massima consentita è di 30 caratteri")
    private String name;
    @NotBlank(message = "La descrizione non può essere vuota")
    @Size(max = 255, message = "La lunghezza massima consentita è di 255 caratteri")
    private String description;

    @URL
    private String img;

    @DecimalMax(value = "30.00", message = "Il prezzo non può superare i 30€")
    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di 0")
    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime createdAt;

    //    *******************************************************+
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
