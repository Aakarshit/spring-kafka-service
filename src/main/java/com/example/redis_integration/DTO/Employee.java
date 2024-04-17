package com.example.redis_integration.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {
    private Integer id;

    private String name;

    private String description;
}
