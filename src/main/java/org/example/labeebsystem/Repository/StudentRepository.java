package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
