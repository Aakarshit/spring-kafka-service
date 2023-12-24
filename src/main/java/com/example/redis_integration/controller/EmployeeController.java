package com.example.redis_integration.controller;

import com.example.redis_integration.DTO.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("redis-test")
public class EmployeeController {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;

    @PostMapping("/employee")
    @Cacheable(cacheManager = "shortTermCache",cacheNames = "test")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        redisTemplate.opsForValue().set(employee.getId().toString(),employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @GetMapping("employee/{id}")
    @Cacheable(cacheManager = "shortTermCache",cacheNames = "test-2")
    public ResponseEntity<Employee> getEmployee(@PathVariable String id){
        Employee employee= (Employee) redisTemplate.opsForValue().get(id);
        if(employee==null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(employee);
    }
}
