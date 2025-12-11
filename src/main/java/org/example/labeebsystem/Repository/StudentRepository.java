package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Parent;
import org.example.labeebsystem.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findStudentById(Integer id);

    Student findStudentByParent(Parent parent);

    List<Student> findAllByParent(Parent parent);

    @Query("select s from Student s join s.courseSchedules cs where cs.course.id = :courseId")
    List<Student> findStudentsByCourseId(Integer courseId);


}
