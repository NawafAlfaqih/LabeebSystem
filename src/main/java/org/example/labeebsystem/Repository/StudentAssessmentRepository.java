package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.StudentAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAssessmentRepository extends JpaRepository<StudentAssessment,Integer> {
    StudentAssessment findStudentAssessmentById(Integer id);
}
