package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.DTO_out.AttendanceReportDTO;
import org.example.labeebsystem.Model.*;
import org.example.labeebsystem.Repository.AdminRepository;
import org.example.labeebsystem.Repository.CourseRepository;
import org.example.labeebsystem.Repository.SessionRepository;
import org.example.labeebsystem.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
private final AdminRepository adminRepository;

private final StudentRepository studentRepository;


public List<Session> getAllSessions(Integer adminId) {
    Admin admin = adminRepository.findAdminById(adminId);
    if (admin == null)
        throw new ApiException("sorry Only admin can view all sessions");

    return sessionRepository.findAll();
}


    public void addSession(Integer courseId, Session session) {

        Course course = courseRepository.findCourseById(courseId);
        if (course == null)
            throw new ApiException("Course not found");
        CourseSchedule schedule = course.getCourseSchedule();

        if (schedule == null)
            throw new ApiException("Course does not have a schedule yet cannot add session");

        LocalDate today = LocalDate.now();
        if (today.isBefore(schedule.getStart_date()) || today.isAfter(schedule.getEnd_date()))
            throw new ApiException("Cannot create session today, date is outside the course schedule");

        session.setDate(today);
        session.setCourse(course);
        sessionRepository.save(session);
    }



    public void updateSession(Integer sessionId, Session updatedSession) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null)
            throw new ApiException("session not fount");
        if (updatedSession.getSession_url() != null)
            session.setSession_url(updatedSession.getSession_url());

        if (updatedSession.getAttendance() != null)
            session.setAttendance(updatedSession.getAttendance());

        if (updatedSession.getDate() != null) {
            CourseSchedule schedule = session.getCourse().getCourseSchedule();
            if (updatedSession.getDate().isBefore(schedule.getStart_date()) || updatedSession.getDate().isAfter(schedule.getEnd_date()))
                throw new ApiException("Updated date is outside the course schedule");
            session.setDate(updatedSession.getDate());
        }
        sessionRepository.save(session);
    }

    public void deleteSession(Integer sessionId) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null)
            throw new ApiException("Session not found");
        sessionRepository.delete(session);
    }


    //الحضور التفصيلي
    public AttendanceReportDTO getAttendanceReport(Integer studentId, Integer courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course == null)
            throw new ApiException("Course not found");

        Student student = studentRepository.findStudentById(studentId);
        if (student == null)
            throw new ApiException("Student not found");
        List<Session> sessions = sessionRepository.findAll();
        int total = 0, attended = 0, absent = 0, late = 0;
        for (Session s : sessions) {
            if (s.getCourse().getId().equals(courseId) && s.getStudent().getId().equals(studentId)) {
                total++;
                if (s.getAttendance().equals("Attended")) attended++;
                else if (s.getAttendance().equals("Absent")) absent++;
                else if (s.getAttendance().equals("Late")) late++;
            }
        }
        double percentage = 0.0;
        if (total > 0) {
            percentage = (attended * 100.0) / total;
        }
        return new AttendanceReportDTO(
                course.getTitle(),
                total,
                attended,
                absent,
                late,
                percentage
        );
    }

}
