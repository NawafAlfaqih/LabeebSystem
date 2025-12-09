package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Model.Task;
import org.example.labeebsystem.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    Task findTaskById(Integer id);

    List<Task> findTaskByTeacher(Teacher teacher);

    Task findTaskByStudentAndTeacher(Student student, Teacher teacher);
}
