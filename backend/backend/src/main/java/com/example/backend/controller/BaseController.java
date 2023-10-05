package com.example.backend.controller;

import com.example.backend.entity.Todo;
import com.example.backend.repository.RRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @Autowired
    private RRepository repository;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public Iterable<Todo> asdf() {
//        repository.save(new Todo("1","dance"));
//        repository.save(new Todo("2","sing"));
        System.out.println(repository.findAll());
//        System.out.println(redisTemplate.opsForList().rightPush("todos", new Todo("1", "dance")));
        System.out.println(redisTemplate.opsForList().indexOf("todos", new Todo("1", "dance")));
//        redisTemplate.opsForList().
        return redisTemplate.opsForList().range("todos", 0, -1);
    }
}
