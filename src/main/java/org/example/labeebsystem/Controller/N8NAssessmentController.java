package org.example.labeebsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.DTO_out.EnglishAssessmentRequestDTO;
import org.example.labeebsystem.DTO_in.EnglishAssessmentResultDTO;
import org.example.labeebsystem.Model.StudentAssessment;
import org.example.labeebsystem.Service.N8nEnglishAssessmentService;
import org.example.labeebsystem.Service.StudentAssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/n8n")
@RequiredArgsConstructor
public class N8NAssessmentController {
    // http://host.docker.internal:8080/api/v1/n8n the base URL

    private final StudentAssessmentService studentAssessmentService;

    @GetMapping("/get-all/{adminId}")
    public ResponseEntity<?> getAllAssessments(@PathVariable Integer adminId){
        return ResponseEntity.status(200).body(studentAssessmentService.getAllAssessments(adminId));
    }

    @PostMapping("/assessment-request")
    public ResponseEntity<?> requestAssessment(@RequestBody EnglishAssessmentRequestDTO dto) {
        studentAssessmentService.addStudentAssessment(dto);
        return ResponseEntity.status(200).body(new ApiResponse("assessment added successfully"));
    }

    @DeleteMapping("/delete-assessment/{studentId}/{assessmentId}")
    public ResponseEntity<?> deleteAssessment(@PathVariable Integer studentId,@PathVariable Integer assessmentId){
        studentAssessmentService.deleteStudentAssessment(studentId,assessmentId);
        return ResponseEntity.status(200).body(new ApiResponse("assessment deleted successfully"));
    }



}