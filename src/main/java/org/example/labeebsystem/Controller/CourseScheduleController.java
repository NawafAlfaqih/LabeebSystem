package org.example.labeebsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Model.CourseSchedule;
import org.example.labeebsystem.Service.CourseScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/schedule")
public class CourseScheduleController {

    private final CourseScheduleService courseScheduleService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllSchedules() {
        return ResponseEntity.status(200).body(courseScheduleService.getAllSchedules());
    }

    @PostMapping("/add/teacher-id/{teacherId}/course-id/{courseId}")
    public ResponseEntity<?> addSchedule(@PathVariable Integer teacherId, @PathVariable Integer courseId,
                                         @Valid @RequestBody CourseSchedule schedule) {
        courseScheduleService.addSchedule(teacherId, courseId, schedule);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Course schedule added successfully"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getScheduleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(courseScheduleService.getScheduleById(id));
    }

    @PutMapping("/update/id/{id}/teacher-id/{teacherId}")
    public ResponseEntity<?> updateSchedule(@PathVariable Integer id, @PathVariable Integer teacherId, @Valid @RequestBody CourseSchedule schedule) {
        courseScheduleService.updateSchedule(id, teacherId, schedule);
        return ResponseEntity.status(200).body(new ApiResponse("Course schedule updated successfully"));
    }

    @DeleteMapping("/delete/id/{id}/teacher-id/{teacherId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer id, @PathVariable Integer teacherId) {
        courseScheduleService.deleteSchedule(id, teacherId);
        return ResponseEntity.status(200).body(new ApiResponse("Course schedule deleted successfully"));
    }
}
