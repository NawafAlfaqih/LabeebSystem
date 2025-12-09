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

    @PostMapping("/add/{courseId}")
    public ResponseEntity<?> addSchedule(@PathVariable Integer courseId,
                                         @Valid @RequestBody CourseSchedule schedule) {
        courseScheduleService.addSchedule(courseId, schedule);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Course schedule added successfully"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getScheduleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(courseScheduleService.getScheduleById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Integer id, @Valid @RequestBody CourseSchedule schedule) {
        courseScheduleService.updateSchedule(id, schedule);
        return ResponseEntity.status(200).body(new ApiResponse("Course schedule updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer id) {
        courseScheduleService.deleteSchedule(id);
        return ResponseEntity.status(200).body(new ApiResponse("Course schedule deleted successfully"));
    }
}
