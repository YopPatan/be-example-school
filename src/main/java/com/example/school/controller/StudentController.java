package com.example.school.controller;

import com.example.school.bean.PageResponse;
import com.example.school.entity.StudentEntity;
import com.example.school.entity.StudentEntity;
import com.example.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<StudentEntity>> getPageStudents(@RequestParam(defaultValue = "0") Integer page) {
        PageResponse<StudentEntity> studentEntities = studentService.getPageStudents(page, 10);
        return new ResponseEntity(studentEntities, HttpStatus.OK);
    }
    
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentEntity>> getAllStudents() {
        List<StudentEntity> studentEntities = studentService.getAllStudents();
        return new ResponseEntity(studentEntities, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentEntity> getAllStudents(@PathVariable int id) {
        Optional<StudentEntity> studentEntity = studentService.getStudentById(id);
        if (studentEntity.isPresent()) {
            return new ResponseEntity(studentEntity.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createStudent(@RequestBody StudentEntity requestStudentEntity) {
        Optional<StudentEntity> studentEntity = studentService.createStudent(requestStudentEntity);
        if (studentEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateStudent(@PathVariable int id,
                                       @RequestBody StudentEntity requestStudentEntity) {
        Optional<StudentEntity> studentEntity = studentService.updateStudent(id, requestStudentEntity);
        if (studentEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteStudent(@PathVariable int id) {
        Optional<StudentEntity> studentEntity = studentService.deleteStudent(id);
        if (studentEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
