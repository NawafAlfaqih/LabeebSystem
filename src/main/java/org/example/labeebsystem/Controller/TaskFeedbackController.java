package org.example.labeebsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Model.TaskFeedback;
import org.example.labeebsystem.Service.TaskFeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/feedback")
public class TaskFeedbackController {

    private final TaskFeedbackService taskFeedbackService;



    @GetMapping("/get")
    public ResponseEntity<?> getAllFeedbacks() {
        return ResponseEntity.status(200).body(taskFeedbackService.getAllFeedbacks());
    }


    @PostMapping("/add/{taskId}/{teacherId}")
    public ResponseEntity<?> addFeedback(@PathVariable Integer taskId, @PathVariable Integer teacherId, @Valid @RequestBody TaskFeedback feedback) {
        taskFeedbackService.addFeedback(taskId, teacherId, feedback);
        return ResponseEntity.status(201).body(new ApiResponse("Feedback added successfully"));
    }

    @PutMapping("/update/{feedbackId}/{teacherId}")
    public ResponseEntity<?> updateFeedback(@PathVariable Integer feedbackId, @PathVariable Integer teacherId, @Valid @RequestBody TaskFeedback feedback) {
        taskFeedbackService.updateFeedback(feedbackId, teacherId, feedback);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback updated successfully"));
    }

    @DeleteMapping("/delete/{feedbackId}/{teacherId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Integer feedbackId, @PathVariable Integer teacherId) {
        taskFeedbackService.deleteFeedback(feedbackId, teacherId);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback deleted successfully"));
    }
}
