package com.example.school.service;

import com.example.school.entity.StudentEntity;
import com.example.school.repository.CourseRepository;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<StudentEntity> getAllStudents() {
        List<StudentEntity> studentEntities = studentRepository.findAll();
        return studentEntities;
    }

    public Optional<StudentEntity> getStudentById(int id) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        return studentEntity;
    }

    public Optional<StudentEntity> createStudent(StudentEntity studentEntity) {
        return this.saveStudent(Optional.of(studentEntity));
    }

    public Optional<StudentEntity> updateStudent(int id, StudentEntity studentEntity) {
        Optional<StudentEntity> savedStudent = studentRepository.findById(id);

        if (savedStudent.isPresent()) {
            if (!studentEntity.getName().isEmpty()) {
                savedStudent.get().setName(studentEntity.getName());
            }
            if (!studentEntity.getLastName().isEmpty()) {
                savedStudent.get().setLastName(studentEntity.getLastName());
            }
            if (!studentEntity.getRut().isEmpty()) {
                savedStudent.get().setRut(studentEntity.getRut());
            }
            if (studentEntity.getAge() != null) {
                savedStudent.get().setAge(studentEntity.getAge());
            }
            if (studentEntity.getCourseId() != null) {
                savedStudent.get().setCourseId(studentEntity.getCourseId());
            }

            savedStudent = this.saveStudent(savedStudent);

        }
        return savedStudent;
    }

    public Optional<StudentEntity> deleteStudent(int id) {
        Optional<StudentEntity> deleteStudent = studentRepository.findById(id);

        if (deleteStudent.isPresent()) {
            try {
                studentRepository.delete(deleteStudent.get());
            } catch (Exception ex) {
                deleteStudent = Optional.empty();
            }
        }
        return deleteStudent;
    }

    private Optional<StudentEntity> saveStudent(Optional<StudentEntity> studentEntity) {
        if (!this.isValidStudent(studentEntity.get())) {
            studentEntity = Optional.empty();
        }
        else {
            try {
                studentEntity = Optional.of(studentRepository.save(studentEntity.get()));
            } catch (Exception ex) {
                studentEntity = Optional.empty();
            }
        }
        return studentEntity;
    }

    private boolean isValidStudent(StudentEntity studentEntity) {
        if (!courseRepository.findById(studentEntity.getCourseId()).isPresent()) {
            return false;
        }

        if (studentEntity.getName().isEmpty() ||
                studentEntity.getLastName().isEmpty() ||
                studentEntity.getRut().isEmpty() ||
                studentEntity.getAge() == null ||
                studentEntity.getCourseId() == null ||
                !isValidRut(studentEntity.getRut()) ||
                studentEntity.getAge() < 18) {
            return false;
        }

        return true;
    }

    private static boolean isValidRut(String rut) {

        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }
    
}
