package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.StudentPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Integer> {

    StudentPayment findStudentPaymentById(Integer studentPaymentId);

    List<StudentPayment> findAllByRefundRequested(Boolean refundRequested);

}
