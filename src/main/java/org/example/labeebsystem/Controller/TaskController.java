package org.example.labeebsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.Model.Task;
import org.example.labeebsystem.Service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/get/{adminId}")
    public ResponseEntity<?> getAllTasks(@PathVariable Integer adminId) {
        return ResponseEntity.status(200).body(taskService.getAllTasks(adminId));
    }
    @PostMapping("/add/teacher/{studentId}/{teacherId}")
    public ResponseEntity<?> addTeacherTask(@RequestBody Task task, @PathVariable Integer studentId,
                                            @PathVariable Integer teacherId) {
        taskService.addTeacherTask(task, studentId, teacherId);
        return ResponseEntity.status(200).body("Task added by teacher successfully");
    }
    @PostMapping("/add/parent/{studentId}/{teacherId}/{parentId}")
    public ResponseEntity<?> addParentTask(@RequestBody Task task, @PathVariable Integer studentId,
            @PathVariable Integer teacherId, @PathVariable Integer parentId) {
        taskService.addParentTask(task, studentId, teacherId, parentId);
        return ResponseEntity.status(200).body("Task added by parent successfully");
    }

    @PutMapping("/update/{taskId}/{teacherId}")
    public ResponseEntity<?> updateTask(@PathVariable Integer taskId, @PathVariable Integer teacherId,
                                        @RequestBody Task task) {
        taskService.updateTask(taskId, task, teacherId);
        return ResponseEntity.status(200).body("Task updated successfully");
    }

    @DeleteMapping("/delete/teacher/{taskId}/{teacherId}")
    public ResponseEntity<?> deleteTeacherTask(@PathVariable Integer taskId, @PathVariable Integer teacherId) {
        taskService.deleteTeacherTask(taskId, teacherId);
        return ResponseEntity.status(200).body("Task deleted by teacher successfully");
    }

    @DeleteMapping("/delete/parent/{taskId}/{parentId}")
    public ResponseEntity<?> deleteParentTask(@PathVariable Integer taskId, @PathVariable Integer parentId) {
        taskService.deleteParentTask(taskId, parentId);
        return ResponseEntity.status(200).body("Task deleted by parent successfully");
    }
//هذي المهام الي راح وقتها وماسواها المدرس
    @GetMapping("/expired/{teacherId}")
    public ResponseEntity<?> getExpiredTasks(@PathVariable Integer teacherId) {
        return ResponseEntity.status(200).body(taskService.getExpiredUncorrectedTasks(teacherId));
    }

}
