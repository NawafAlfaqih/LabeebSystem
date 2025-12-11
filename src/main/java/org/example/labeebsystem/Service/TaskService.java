package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.*;
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
    private final CourseScheduleRepository courseScheduleRepository;
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

        Course course = courseRepository.findCourseByTeacher(teacher);
        if (courseScheduleRepository.findCourseScheduleByStudentAndCourse(student, course) == null)
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

        Course course = courseRepository.findCourseByTeacher(teacher);
        if (courseScheduleRepository.findCourseScheduleByStudentAndCourse(student, course) == null)
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

//هذي اذا صحح الاستاذ، تتعدل حالته وينخصم فلوس
    public void correctTask(Integer taskId, Integer teacherId, Integer grade) {

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        Task task = taskRepository.findTaskById(taskId);
        if (task == null)
            throw new ApiException("Task not found");

        if (!task.getTeacher().getId().equals(teacherId))
            throw new ApiException("You are not allowed to correct this task");

        if (task.getStatus().equals("Approved"))
            throw new ApiException("This task is already corrected");
        task.setGrade(grade);
        task.setStatus("Approved");

        double amount = grade / 10.0;

        Parent parent = task.getParent();
        Student student = task.getStudent();

        if (parent == null || student == null)
            throw new ApiException("Parent or Student not found");

        if (parent.getBalance() < amount)
            throw new ApiException("Parent balance is not enough");

        parent.setBalance(parent.getBalance() - amount);
        student.setBalance(student.getBalance() + amount);

        parentRepository.save(parent);
        studentRepository.save(student);
        taskRepository.save(task);
    }

    //هذي التاسكات الي رفعها الاب
    public List<Task> getTasksUploadedByParent(Integer parentId) {

        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null) {
            throw new ApiException("Parent not found");
        }
        return taskRepository.findAllByParentAndCreatedBy(parent, "parent");
    }


    //هذي التاسكات الي رفعها التيتشر
    public List<Task> getTasksUploadedByTeacher(Integer teacherId) {
        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null) {
            throw new ApiException("Teacher not found");
        }
        return taskRepository.findAllByTeacherAndCreatedBy(teacher, "teacher");
    }

//التاسكات الي حالها بندنق ماتسلمت
public List<Task> getPendingTasksForStudent(Integer studentId) {

    Student student = studentRepository.findStudentById(studentId);
    if (student == null)
        throw new ApiException("Student not found");
    List<Task> taskList = taskRepository.findAll();
    List<Task> result = new ArrayList<>();
    for (Task t : taskList) {
        if (t.getStudent() != null && t.getStudent().getId().equals(studentId) && t.getStatus().equalsIgnoreCase("Pending")) {
            result.add(t);
        }
    }
    if (result.isEmpty())
        throw new ApiException("No pending tasks found for this student");

    return result;
}



}
