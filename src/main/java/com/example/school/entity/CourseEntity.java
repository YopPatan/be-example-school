package com.example.school.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private String code;

    /*
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<StudentEntity> students = new ArrayList<>();
     */
}
