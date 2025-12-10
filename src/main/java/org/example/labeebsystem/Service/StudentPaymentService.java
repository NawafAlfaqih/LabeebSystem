package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.*;
import org.example.labeebsystem.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentPaymentService {

    private final StudentPaymentRepository studentPaymentRepository;
    private final StudentRepository studentRepository;
    private final CourseScheduleRepository courseScheduleRepository;
    private final AdminRepository adminRepository;
    private final ParentRepository parentRepository;


    public List<StudentPayment> getAllStudentCourses(Integer adminId) {
        if (adminRepository.findAdminById(adminId) == null)
            throw new ApiException("Admin not found");

        return studentPaymentRepository.findAll();
    }

    public void buyCourseFullPayment(Integer parentId, Integer studentId,
                                     Integer courseScheduleId, StudentPayment studentPayment) {
        CourseSchedule courseSchedule = courseScheduleRepository.findCourseScheduleById(courseScheduleId);
        if (courseSchedule == null)
            throw new ApiException("CourseSchedule was not found");

        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null)
            throw new ApiException("Parent was not found");

        Student student = studentRepository.findStudentById(studentId);
        if (student == null)
            throw new ApiException("Student was not found");

        if (!parent.equals(student.getParent()))
            throw new ApiException("Not correct parent for student");

        if (parent.getBalance() < courseSchedule.getCourse().getPrice())
            throw new ApiException("Balance is not sufficient");

        Set<CourseSchedule> courseSchedules = student.getCourseSchedules();
        for (CourseSchedule cs: courseSchedules) {
            if (cs.getDay().equals(courseSchedule.getDay()))
                throw new ApiException("Cannot have two courses in the same day");
        }

        //Todo: DiscountCode fullPayment
        studentPayment.setFinalPrice(
                        studentPayment.getCourseSchedule()
                        .getCourse()
                        .getPrice()
        );
        studentPayment.setTotalInstallments(1);
        studentPayment.setRemainingInstallments(0);
        studentPayment.setInstallmentAmount(studentPayment.getFinalPrice());

        courseSchedule.setAvailability("Registered");
        courseSchedule.setStudent(student);

        studentPayment.setPaymentType("Full");
        studentPayment.setCourseSchedule(courseSchedule);
        studentPaymentRepository.save(studentPayment);
    }

    public void buyCourseInstallmentsPayment(Integer parentId, Integer studentId,
                                     Integer courseScheduleId, StudentPayment studentPayment) {
        CourseSchedule courseSchedule = courseScheduleRepository.findCourseScheduleById(courseScheduleId);
        if (courseSchedule == null)
            throw new ApiException("CourseSchedule was not found");

        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null)
            throw new ApiException("Parent was not found");

        Student student = studentRepository.findStudentById(studentId);
        if (student == null)
            throw new ApiException("Student was not found");

        if (!parent.equals(student.getParent()))
            throw new ApiException("Not correct parent for student");

        if (parent.getBalance() < courseSchedule.getCourse().getPrice()/4)
            throw new ApiException("Balance is not sufficient");

        Set<CourseSchedule> courseSchedules = student.getCourseSchedules();
        for (CourseSchedule cs: courseSchedules) {
            if (cs.getDay().equals(courseSchedule.getDay()))
                throw new ApiException("Cannot have two courses in the same day");
        }

        //Todo: DiscountCode fullPayment
        studentPayment.setFinalPrice(
                studentPayment.getCourseSchedule()
                        .getCourse()
                        .getPrice()
        );
        studentPayment.setTotalInstallments(4);
        studentPayment.setRemainingInstallments(3);
        studentPayment.setInstallmentAmount(studentPayment.getFinalPrice()/4);

        courseSchedule.setAvailability("Registered");
        courseSchedule.setStudent(student);

        studentPayment.setPaymentType("Installments");
        studentPayment.setCourseSchedule(courseSchedule);
        studentPaymentRepository.save(studentPayment);
    }

    //Todo: Pay installment دفع دفعه

}
