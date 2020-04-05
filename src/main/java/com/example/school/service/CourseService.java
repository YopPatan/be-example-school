package com.example.school.service;

import com.example.school.bean.PageResponse;
import com.example.school.entity.CourseEntity;
import com.example.school.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public PageResponse<CourseEntity> getPageCourses(int page, int size) {
        Pageable coursePageable = PageRequest.of(page, size);
        Page<CourseEntity> courseEntitiesPage = courseRepository.findAll(coursePageable);
        PageResponse<CourseEntity> courseEntities = new PageResponse<>();
        courseEntities.setContent(courseEntitiesPage.getContent());
        courseEntities.setNextPage((courseEntitiesPage.hasNext()) ? "?page=" + (page+1) : null);
        courseEntities.setPrevPage((courseEntitiesPage.hasPrevious()) ? "?page=" + (page-1) : null);
        return courseEntities;
    }

    public Optional<CourseEntity> getCourseById(int id) {
        Optional<CourseEntity> courseEntity = courseRepository.findById(id);
        return courseEntity;
    }

    public Optional<CourseEntity> createCourse(CourseEntity courseEntity) {
        return this.saveCourse(Optional.of(courseEntity));
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
            savedCourse = this.saveCourse(savedCourse);

        }
        return savedCourse;
    }

    public Optional<CourseEntity> deleteCourse(int id) {
        Optional<CourseEntity> deleteCourse = courseRepository.findById(id);

        if (deleteCourse.isPresent()) {
            try {
                courseRepository.delete(deleteCourse.get());
            } catch (Exception ex) {
                deleteCourse = Optional.empty();
            }
        }
        return deleteCourse;
    }

    private Optional<CourseEntity> saveCourse(Optional<CourseEntity> courseEntity) {
        if (!this.isValidCourse(courseEntity.get())) {
            courseEntity = Optional.empty();
        }
        else {
            try {
                courseEntity = Optional.of(courseRepository.save(courseEntity.get()));
            } catch (Exception ex) {
                courseEntity = Optional.empty();
            }
        }
        return courseEntity;
    }

    private boolean isValidCourse(CourseEntity courseEntity) {
        if (courseEntity.getName().isEmpty() ||
                courseEntity.getCode().isEmpty() ||
                courseEntity.getCode().length() > 4) {
            return false;
        }
        return true;
    }

}
