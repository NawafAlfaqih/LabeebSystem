package org.example.labeebsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Model.Admin;
import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Repository.StudentRepository;
import org.example.labeebsystem.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/get/admin-id/{adminId}")
    public ResponseEntity<?> getAllStudents(@PathVariable Integer adminId){
        return ResponseEntity.status(200).body(studentService.getAllStudents(adminId));
    }

    @PostMapping("/add/parent-id/{parentId}")
    public ResponseEntity<?> addStudent(@PathVariable Integer parentId, @RequestBody @Valid Student student){
        studentService.addStudent(parentId, student);
        return ResponseEntity.status(200).body(new ApiResponse("Student added successfully"));
    }

    @PutMapping("/update/student-id/{id}/parent-id/{parentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @PathVariable Integer parentId, @RequestBody @Valid Student student){
        studentService.updateStudent(id, parentId, student);
        return ResponseEntity.status(200).body(new ApiResponse("Student updated successfully"));
    }

    @DeleteMapping("/delete/student-id/{id}/parent-id/{parentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @PathVariable Integer parentId){
        studentService.deleteStudent(id, parentId);
        return ResponseEntity.status(200).body(new ApiResponse("Student deleted successfully"));
    }
//يطلع الستويدنت كلهم بناء علي اي دي الاب
    @GetMapping("/by-parent/{parentId}")
    public ResponseEntity<?> getStudentsByParent(@PathVariable Integer parentId) {
        return ResponseEntity.status(200).body(studentService.getStudentsByParent(parentId));
    }
//ليدر بورد اعلى قريد
    @GetMapping("/leaderboard/{courseId}")
    public ResponseEntity<?> getLeaderboard(@PathVariable Integer courseId) {
        return ResponseEntity.status(200).body(studentService.getLeaderboard(courseId));
    }


}
