package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Cafe_Data")
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @Size(min = 10)
    private String name;

    @NotNull
    @Size(min = 10)
    private String menuitem;

    @NotNull
    @Size(min = 10)
    private String description;

    private String picture;

    private int price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(String menuitem) {
        this.menuitem = menuitem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}