package com.example.servlet20;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Car {
    private String id;
    private String name;
    private Integer price;
    private Integer horsePower;

    public Car() {
    }

    public Car(String id, String name, Integer price, Integer horsePower) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.horsePower = horsePower;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", horsePower=" + horsePower +
                '}';
    }

}

