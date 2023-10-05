package com.example.backend.repository;

import com.example.backend.entity.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RRepository extends CrudRepository<Todo, String> {
}
