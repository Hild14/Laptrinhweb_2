package com.example.demo.model; // Chú ý dòng này đã sửa thành demo

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private int age;

    // Getter và Setter (Bắt buộc phải có để lấy dữ liệu)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}