package org.example.labeebsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.DTO_in.ExcuseDTO;
import org.example.labeebsystem.Model.Session;
import org.example.labeebsystem.Service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/get-all/{adminId}")
    public ResponseEntity<?> getAllSessions(@PathVariable Integer adminId) {
        return ResponseEntity.status(200).body(sessionService.getAllSessions(adminId));
    }


    @PostMapping("/add/{courseId}")
    public ResponseEntity<?> addSession(@PathVariable Integer courseId, @RequestBody @Valid Session session) {
        sessionService.addSession(courseId, session);
        return ResponseEntity.status(200).body(new ApiResponse("Session added successfully"));
    }

    @PutMapping("/update/{sessionId}")
    public ResponseEntity<?> updateSession(@PathVariable Integer sessionId, @RequestBody @Valid Session session) {
        sessionService.updateSession(sessionId, session);
        return ResponseEntity.status(200).body(new ApiResponse("Session updated successfully"));
    }

    @DeleteMapping("/delete/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable Integer sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("session deleted successfully"));
    }
//حقت الحضور التفصيلي
    @GetMapping("/attendance/{studentId}/{courseId}")
    public ResponseEntity<?> getAttendanceReport(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        return ResponseEntity.status(200).body(sessionService.getAttendanceReport(studentId, courseId));
    }
//اعذار الغياب
@PostMapping("/excuse/{studentId}/{sessionId}")
    public ResponseEntity<?> uploadExcuse(@PathVariable Integer studentId, @PathVariable Integer sessionId, @RequestBody ExcuseDTO dto) {
    sessionService.uploadExcuse(studentId, sessionId, dto);
    return ResponseEntity.status(200).body("Excuse submitted successfully");
}
}
