package org.example.labeebsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.DTO_in.TeacherDTOIN;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllTeachers(){
        return ResponseEntity.status(200).body(teacherService.getAllTeachers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTeacher(@RequestBody @Valid TeacherDTOIN teacher){
        teacherService.addTeacher(teacher);
        return ResponseEntity.status(200).body(new ApiResponse("teacher added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Integer id, @RequestBody @Valid TeacherDTOIN teacher){
        teacherService.updateTeacher(id, teacher);
        return ResponseEntity.status(200).body(new ApiResponse("teacher updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Integer id){
        teacherService.deleteTeacher(id);
        return ResponseEntity.status(200).body(new ApiResponse("teacher deleted successfully"));
    }

    // it will return only the active teachers
    @GetMapping("/get-by-category/{categoryId}")
    public ResponseEntity<?> getTeachersByCategory(@PathVariable Integer categoryId){
        return ResponseEntity.status(200).body(teacherService.getTeachersByCategory(categoryId));
    }

    @GetMapping("/get-by-rating")
    public ResponseEntity<?> getTeachersOrderedByRating(){
        return ResponseEntity.status(200).body(teacherService.getTeachersOrderedByRating());
    }

    @GetMapping("/get-pending-teachers/{adminID}")
    public ResponseEntity<?> getAllPendingTeachers(@PathVariable Integer adminID){
        return ResponseEntity.status(200).body(teacherService.getAllPendingTeachers(adminID));
    }
//يقبل المدرس
    @PutMapping("/accept-teacher/{adminId}/{teacherId}")
    public ResponseEntity<?>  acceptTeacher(@PathVariable Integer adminId, @PathVariable Integer teacherId){
        teacherService.acceptTeacher(adminId,teacherId);
        return ResponseEntity.status(200).body(new ApiResponse("teacher accepted successfully"));
    }
//يرفض المدرس
    @PutMapping("/reject-teacher/{adminId}/{teacherId}")
    public ResponseEntity<?>  rejectTeacher(@PathVariable Integer adminId, @PathVariable Integer teacherId){
        teacherService.rejectTeacher(adminId,teacherId);
        return ResponseEntity.status(200).body(new ApiResponse("teacher reject successfully"));
    }

}