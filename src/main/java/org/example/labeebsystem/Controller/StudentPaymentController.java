package org.example.labeebsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Model.StudentPayment;
import org.example.labeebsystem.Service.StudentPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/student-payment")
public class StudentPaymentController {

    private final StudentPaymentService studentPaymentService;

    @GetMapping("/get/{adminId}")
    public ResponseEntity<?> getAllStudentCourses(@PathVariable Integer adminId) {
        List<StudentPayment> payments = studentPaymentService.getAllStudentCourses(adminId);
        return ResponseEntity.status(200).body(payments);
    }

    @PostMapping("/buy/full/{parentId}/{studentId}/{courseScheduleId}")
    public ResponseEntity<?> buyCourseFullPayment(@PathVariable Integer parentId, @PathVariable Integer studentId,
            @PathVariable Integer courseScheduleId, @RequestBody StudentPayment studentPayment) {
        studentPaymentService.buyCourseFullPayment(parentId, studentId, courseScheduleId, studentPayment);
        return ResponseEntity.status(200).body(new ApiResponse("Course purchased successfully (Full Payment)"));
    }

    @PostMapping("/buy/installments/{parentId}/{studentId}/{courseScheduleId}")
    public ResponseEntity<?> buyCourseInstallmentsPayment(@PathVariable Integer parentId, @PathVariable Integer studentId,
            @PathVariable Integer courseScheduleId, @RequestBody StudentPayment studentPayment) {
        studentPaymentService.buyCourseInstallmentsPayment(parentId, studentId, courseScheduleId, studentPayment);
        return ResponseEntity.status(200).body(new ApiResponse("Course purchased successfully (Installments Payment)"));
    }
    //طلب استرجاع
    @PostMapping("/refund/{parentId}/{paymentId}")
    public String requestRefund(@PathVariable Integer parentId, @PathVariable Integer paymentId, @RequestBody String message) {
        return studentPaymentService.requestRefund(parentId, paymentId, message);
    }

    //قبول الاسترجاع
    @PutMapping("/refund/{adminId}/{paymentId}")
    public String processRefund(@PathVariable Integer adminId, @PathVariable Integer paymentId, @RequestBody @Valid boolean approve) {

    return studentPaymentService.processRefund(adminId, paymentId, approve);
    }
//تقسيط
    @PutMapping("/pay-installment/{parentId}/{studentPaymentId}")
    public ResponseEntity<?> payInstallment(@PathVariable Integer parentId, @PathVariable Integer studentPaymentId) {
        studentPaymentService.payInstallment(parentId, studentPaymentId);
        return ResponseEntity.status(200).body("Installment paid successfully");
    }

}
