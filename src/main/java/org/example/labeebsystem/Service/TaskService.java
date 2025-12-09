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
        task.setCreatedAt(LocalDateTime.now());
        task.setParent(student.getParent());
        task.setCreatedBy("Parent");
        taskRepository.save(task);
    }

}
