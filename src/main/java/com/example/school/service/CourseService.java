package com.example.school.service;

import com.example.school.entity.CourseEntity;
import com.example.school.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseEntity> getAllCourses() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        return courseEntities;
    }

    public Optional<CourseEntity> getCourseById(int id) {
        Optional<CourseEntity> courseEntity = courseRepository.findById(id);
        return courseEntity;
    }

    public Optional<CourseEntity> createCourse(CourseEntity courseEntity) {
        Optional<CourseEntity> savedCourse;
        if (courseEntity.getName().isEmpty()) {
            savedCourse = Optional.empty();
        } else if (courseEntity.getCode().isEmpty()) {
            savedCourse = Optional.empty();
        } else {
            try {
                savedCourse = Optional.of(courseRepository.save(courseEntity));
            } catch (Exception ex) {
                savedCourse = Optional.empty();
            }
        }
        return savedCourse;
    }

    public Optional<CourseEntity> updateCourse(int id, CourseEntity courseEntity) {
        Optional<CourseEntity> savedCourse = courseRepository.findById(id);

        if (savedCourse.isPresent()) {
            if (!courseEntity.getName().isEmpty()) {
                savedCourse.get().setName(courseEntity.getName());
            }
            if (!courseEntity.getCode().isEmpty()) {
                savedCourse.get().setCode(courseEntity.getCode());
            }

            try {
                savedCourse = Optional.of(courseRepository.save(courseEntity));
            } catch (Exception ex) {
                savedCourse = Optional.empty();
            }
        }
        return savedCourse;
    }

}
