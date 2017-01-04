package com.github.handioq.diber.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price")
    private Double price;

    @NotNull
    @Column(name = "image")
    private String mainImage;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Set<Image> images;

    public Order(String name, String description, Double price, String mainImage, Set<Image> images) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.mainImage = mainImage;
        this.images = images;
    }

    public Order(String name, String description, Double price, String mainImage) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.mainImage = mainImage;
    }

    public Order() {
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty("main_image")
    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    @Override
    public String toString() {
        return "Order{" +
                ", images=" + images +
                '}';
    }
}
