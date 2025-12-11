package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Parent;
import org.example.labeebsystem.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findStudentById(Integer id);

    Student findStudentByParent(Parent parent);

    List<Student> findAllByParent(Parent parent);

}
