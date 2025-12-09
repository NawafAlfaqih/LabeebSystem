package org.example.labeebsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Model.TeacherReview;
import org.example.labeebsystem.Service.TeacherReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teacher-review")
@RequiredArgsConstructor
public class TeacherReviewController {
    private final TeacherReviewService teacherReviewService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllTeacherReview(){
        return ResponseEntity.status(200).body(teacherReviewService.getAllTeacherReviews());
    }

    @PostMapping("/add/{parentId}/{teacherId}")
    public ResponseEntity<?> addAllTeacherReview(@PathVariable Integer parentId,@PathVariable Integer teacherId,@RequestBody @Valid TeacherReview teacherReview){
        teacherReviewService.addTeacherReview(parentId,teacherId,teacherReview);
        return ResponseEntity.status(200).body(new ApiResponse("teacher review added successfully"));
    }


    @PutMapping("/update/{teacherReviewID}")
    public ResponseEntity<?> updateTeacherReview(@PathVariable Integer teacherReviewID,TeacherReview teacherReview){
        teacherReviewService.updateTeacherReview(teacherReviewID,teacherReview);
        return ResponseEntity.status(200).body(new ApiResponse("teacher review updated successfully"));
    }


    @DeleteMapping("/delete/{teacherReviewID}")
    public ResponseEntity<?> deleteTeacherReview(@PathVariable Integer teacherReviewID){
        teacherReviewService.deleteTeacherReview(teacherReviewID);
        return ResponseEntity.status(200).body(new ApiResponse("teacher review deleted successfully"));
    }

}
