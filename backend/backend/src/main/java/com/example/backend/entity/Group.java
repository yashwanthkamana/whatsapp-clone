package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    String uid;
    String name;
    Set<User> membersList;

    public Group(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

}
