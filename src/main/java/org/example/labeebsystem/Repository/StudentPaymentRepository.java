package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.StudentPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Integer> {

    StudentPayment findStudentPaymentById(Integer studentPaymentId);
}
