package com.sd.controller;

import com.sd.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("admin/api/v1/students")
public class AdminStudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"Tom"),
            new Student(2,"Cruise")
    );


    @GetMapping
    public List<Student> getAllStudent() {
        return STUDENTS;
    }

    @PostMapping
    public void registerStudent(@RequestBody Student  student) {
        System.out.println(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println(String.format("%s %s", studentId, student));
    }
}
