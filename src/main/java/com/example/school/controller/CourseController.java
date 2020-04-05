package com.example.school.controller;

import com.example.school.bean.PageResponse;
import com.example.school.entity.CourseEntity;
import com.example.school.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CourseEntity>> getPageCourses(@RequestParam(defaultValue = "0") Integer page) {
        PageResponse<CourseEntity> courseEntities = courseService.getPageCourses(page, 10);
        return new ResponseEntity(courseEntities, HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseEntity>> getAllCourses() {
        List<CourseEntity> courseEntities = courseService.getAllCourses();
        return new ResponseEntity(courseEntities, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseEntity> getAllCourses(@PathVariable int id) {
        Optional<CourseEntity> courseEntity = courseService.getCourseById(id);
        if (courseEntity.isPresent()) {
            return new ResponseEntity(courseEntity.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCourse(@RequestBody CourseEntity requestCourseEntity) {
        Optional<CourseEntity> courseEntity = courseService.createCourse(requestCourseEntity);
        if (courseEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCourse(@PathVariable int id,
                                       @RequestBody CourseEntity requestCourseEntity) {
        Optional<CourseEntity> courseEntity = courseService.updateCourse(id, requestCourseEntity);
        if (courseEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteCourse(@PathVariable int id) {
        Optional<CourseEntity> courseEntity = courseService.deleteCourse(id);
        if (courseEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
