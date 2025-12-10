package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Parent;
import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Model.Task;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ParentRepository parentRepository;

    public List<Task> getAllTasks(Integer adminId) {
        if (adminRepository.findAdminById(adminId) == null)
            throw new ApiException("Admin was not found");

        return taskRepository.findAll();
    }

    public void addTeacherTask(Task task, Integer studentId, Integer teacherId) {
        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        Student student = studentRepository.findStudentById(studentId);
        if (student == null)
            throw new ApiException("Student not found");

        if (courseRepository.findCourseByStudentAndTeacher(student, teacher) == null)
            throw new ApiException("Teacher or Student not in the same Course");

        task.setGrade(0);
        task.setPoints(0);
        task.setStatus("Pending");
        task.setCreatedAt(LocalDateTime.now());
        task.setParent(student.getParent());
        task.setCreatedBy("Teacher");
        taskRepository.save(task);
    }

    public void addParentTask(Task task, Integer studentId, Integer teacherId, Integer parentId) {
        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        Student student = studentRepository.findStudentById(studentId);
        if (student == null)
            throw new ApiException("Student not found");

        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null)
            throw new ApiException("Parent not found");

        if (courseRepository.findCourseByStudentAndTeacher(student, teacher) == null)
            throw new ApiException("Teacher or Student not in the same Course");

        task.setGrade(0);
        task.setPoints(0);
        task.setStatus("Pending");
        task.setCreatedAt(LocalDateTime.now());
        task.setParent(student.getParent());
        task.setCreatedBy("Parent");
        taskRepository.save(task);
    }

    public void updateTask(Integer taskId, Task task, Integer teacherId) {
        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        Task oldTask = taskRepository.findTaskById(taskId);
        if (oldTask == null)
            throw new ApiException("Task not found");

        if (task.getTeacher().equals(teacher))
            throw new ApiException("Teacher cannot update task");

        oldTask.setDescription(task.getDescription());
        oldTask.setDueDate(task.getDueDate());
        oldTask.setFileAnswerUrl(task.getFileAnswerUrl());

        taskRepository.save(oldTask);
    }

    public void deleteTeacherTask(Integer id, Integer teacherId) {
        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        Task task = taskRepository.findTaskById(id);
        if (task == null)
            throw new ApiException("Task not found");

        if (task.getTeacher().equals(teacher))
            throw new ApiException("Teacher cannot delete task");

        taskRepository.delete(task);
    }

    public void deleteParentTask(Integer id, Integer parentId) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null)
            throw new ApiException("Parent not found");

        Task task = taskRepository.findTaskById(id);
        if (task == null)
            throw new ApiException("Task not found");

        if (task.getParent().equals(parent))
            throw new ApiException("Parent cannot delete task");

        taskRepository.delete(task);
    }


//هذي المهام الي ماسواها المدرس وخلص وقتها
    public List<Task> getExpiredUncorrectedTasks(Integer teacherId) {

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");
        List<Task> tasks = taskRepository.findTaskByTeacher(teacher);
        List<Task> result = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        for (Task t : tasks) {
            if (t.getDueDate().isBefore(now) && !t.getStatus().equals("Approved") && t.getGrade() == 0) {
                result.add(t);
            }
        }
        if (result.isEmpty())
            throw new ApiException("No out date tasks found");
        return result;
    }
}
