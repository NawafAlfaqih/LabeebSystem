package org.example.labeebsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Model.Course;
import org.example.labeebsystem.Service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/course")
public class CourseController {

    private final CourseService courseService;


@GetMapping("/get")
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.status(200).body(courses);
    }

    @PostMapping("/add/{teacherId}")
    public ResponseEntity<?> addCourse(@PathVariable Integer teacherId, @RequestBody @Valid Course course) {
        courseService.addCourse(teacherId, course);
        return ResponseEntity.status(200).body(new ApiResponse("Course added successfully"));
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer courseId, @RequestBody @Valid Course course) {
        courseService.updateCourse(courseId, course);
        return ResponseEntity.status(200).body(new ApiResponse("Course updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return ResponseEntity.status(200).body(new ApiResponse("Course deleted successfully"));
    }

    @PutMapping("/assign-schedule/{courseId}/{scheduleId}")
    public ResponseEntity<?> assignCourseSchedule(@PathVariable Integer courseId, @PathVariable Integer scheduleId) {
        courseService.assignCourseSchedule(courseId, scheduleId);
        return ResponseEntity.status(200).body(new ApiResponse("Schedule assigned successfully"));
    }

    @PutMapping("/assign-session/{courseId}/{sessionId}")
    public ResponseEntity<?> assignSessionToCourse(@PathVariable Integer courseId, @PathVariable Integer sessionId) {
        courseService.assignSessionToCourse(courseId, sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("Session assigned successfully"));
    }
}
