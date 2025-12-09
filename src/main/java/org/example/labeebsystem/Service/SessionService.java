package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Admin;
import org.example.labeebsystem.Model.Course;
import org.example.labeebsystem.Model.CourseSchedule;
import org.example.labeebsystem.Model.Session;
import org.example.labeebsystem.Repository.AdminRepository;
import org.example.labeebsystem.Repository.CourseRepository;
import org.example.labeebsystem.Repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
private final AdminRepository adminRepository;


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
}
