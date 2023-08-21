package com.example.bistro.bistroapp.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price; @Column(columnDefinition = "INT DEFAULT 0")
    private int popularity;


@ManyToMany
@JoinTable(
        name = "product_ingredients",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
)
private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new HashSet<>();


    public Product(String name, double price, Set<Ingredient> ingredients, Set<Order> orders) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.orders = orders;
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}


