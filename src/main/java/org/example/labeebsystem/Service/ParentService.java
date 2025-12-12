package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.DTO_out.ParentDTOout;
import org.example.labeebsystem.Model.*;
import org.example.labeebsystem.Repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final AdminRepository adminRepository;
    private final ChatGPTService chatGPTService;
    private final EmailService emailService;
    private final StudentRepository studentRepository;
    private final CourseScheduleRepository courseScheduleRepository;

    private final StudentPaymentRepository studentPaymentRepository;

    public List<ParentDTOout> getAllParents(Integer adminId) {
        if (adminRepository.findAdminById(adminId) == null)
            throw new ApiException("Admin was not found");

        List<Parent> parentList = parentRepository.findAll();
        ArrayList<ParentDTOout> parentDTOs = new ArrayList<>();
        for (Parent p: parentList) {
            ParentDTOout dto = new ParentDTOout(
                    p.getId(),
                    p.getName(),
                    p.getPhoneNumber(),
                    p.getBalance(),
                    p.getEmail(),
                    p.getStudents()
            );
            parentDTOs.add(dto);
        }
        return parentDTOs;
    }

    public void addParent(Parent parent) {
        parent.setBalance(0.0);
        parentRepository.save(parent);
    }

    public void updateParent(Integer parentId, Parent parent) {
        Parent oldParent = parentRepository.findParentById(parentId);
        if (oldParent == null)
            throw new ApiException("Parent was not found");

        oldParent.setEmail(parent.getEmail());
        oldParent.setPassword(parent.getPassword());

        parentRepository.save(parent);
    }

    public void deleteParent(Integer id) {
        Parent parent = parentRepository.findParentById(id);
        if (parent == null)
            throw new ApiException("Parent was not found");

        parentRepository.delete(parent);
    }
//الاب يضيف رصيد
    public void addBalance(Integer parentId, Integer amount) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null) {
            throw new ApiException("Parent not found");
        }
        parent.setBalance(parent.getBalance() + amount);
        parentRepository.save(parent);
    }

//توليد كود الخصم بناد على عدد ابناءه
    public String generateDiscount(Integer parentId) {

        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null)
            throw new ApiException("Parent not found");

        int childrenCount = parent.getStudents().size();

        if (childrenCount < 2)
            throw new ApiException("Discount applies only for parents with 2 or more children");

        int discountPercentage = childrenCount * 5;
        if (discountPercentage > 50)
            discountPercentage = 50;
        String code = "DISC-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        parent.setDiscountCode(code);
        parentRepository.save(parent);

        return "Discount: " + discountPercentage + "% — Code: " + code;
    }
    //تقرير اسبوعي للاب عن ولده
    public String sendWeeklyReport(Integer parentId, Integer studentId, Integer courseScheduleId) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null)
            throw new ApiException("Parent not found");

        Student student = studentRepository.findStudentById(studentId);
        if (student == null || !student.getParent().getId().equals(parentId))
            throw new ApiException("Student not found for this parent");
        CourseSchedule schedule = courseScheduleRepository.findCourseScheduleById(courseScheduleId);

        if (schedule == null || !schedule.getStudent().getId().equals(studentId))
            throw new ApiException("Course not found for this student");
        int attended = 0;
        int absent = 0;
        int pendingTasks = 0;
        int totalGrades = 0;

        LocalDate startOfWeek = LocalDate.now().minusDays(7);

        for (Session session : student.getSessions()) {
            if (session.getCourseSchedule().getId().equals(courseScheduleId) && !session.getDate().isBefore(startOfWeek)) {
                if ("Attended".equalsIgnoreCase(session.getAttendance()))
                    attended++;
                else if ("Absent".equalsIgnoreCase(session.getAttendance()))
                    absent++;
            }
        }
        for (Task task : student.getTasks()) {
            if (!task.getCreatedAt().toLocalDate().isBefore(startOfWeek)) {
                if ("Pending".equalsIgnoreCase(task.getStatus()))
                    pendingTasks++;
                totalGrades += task.getGrade();
            }
        }
    String prompt = """
            Create a short weekly summary for a parent about one student and one course:
            Student name: %s
            Course name: %s
            Attended sessions: %d
            Absent sessions: %d
            Pending tasks: %d
            Total grades earned: %d
            Keep it friendly and clear.
            """.formatted(student.getName(), schedule.getCourse().getTitle(), attended, absent, pendingTasks, totalGrades);

        String aiReport = chatGPTService.askChatGPT(prompt);
        emailService.sendEmail(
                parent.getEmail(),
                "Weekly Student Report", aiReport);
        return "Weekly report has been sent to your email successfully.";
    }

}
