package com.example.servlet20;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String id;
    private String name;
    private Integer price;
    private Integer horsePower;
}
