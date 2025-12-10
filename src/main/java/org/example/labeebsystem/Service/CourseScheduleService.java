package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Course;
import org.example.labeebsystem.Model.CourseSchedule;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Repository.CourseRepository;
import org.example.labeebsystem.Repository.CourseScheduleRepository;
import org.example.labeebsystem.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseScheduleService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseScheduleRepository courseScheduleRepository;


    public List<CourseSchedule> getAllSchedules() {
        return courseScheduleRepository.findAll();
    }

    public void addSchedule(Integer teacherId, Integer courseId, CourseSchedule schedule) {

        Course course = courseRepository.findCourseById(courseId);
        if (course == null)
            throw new ApiException("Course not found");

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        if (!course.getTeacher().equals(teacher))
            throw new ApiException("You are not the teacher for this course");

        Set<CourseSchedule> courseSchedules = course.getCourseSchedules();
        for(CourseSchedule cs : courseSchedules){
            if (isOverlapping(cs, schedule))
                throw new ApiException("Cannot Overlap Schedule");
        }

        if (schedule.getStart_date().isAfter(schedule.getEnd_date()))
            throw new ApiException("Start date must be before end date");

        if (schedule.getStart_time().isAfter(schedule.getEnd_time()))
            throw new ApiException("Start time must be before end time");

        schedule.setAvailability("Available");
        schedule.setCourse(course);
        courseScheduleRepository.save(schedule);
    }


    public CourseSchedule getScheduleById(Integer id) {
        CourseSchedule schedule = courseScheduleRepository.findCourseScheduleById(id);
        if (schedule == null)
            throw new ApiException("Schedule not found");
        return schedule;
    }



    public void updateSchedule(Integer id, Integer teacherId, CourseSchedule updatedSchedule) {

        CourseSchedule schedule = courseScheduleRepository.findCourseScheduleById(id);
        if (schedule == null)
            throw new ApiException("Schedule not found");

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        Set<CourseSchedule> courseSchedules = schedule.getCourse().getCourseSchedules();
        for(CourseSchedule cs : courseSchedules){
            if (isOverlapping(cs, schedule) && !cs.getId().equals(schedule.getId()))
                throw new ApiException("Cannot Overlap Schedule");
        }

        if (!schedule.getCourse().getTeacher().equals(teacher))
            throw new ApiException("You are not the teacher for this course");


        if (updatedSchedule.getStart_date() != null && updatedSchedule.getEnd_date() != null && updatedSchedule.getStart_date().isAfter(updatedSchedule.getEnd_date()))
            throw new ApiException("Start date must be before end date");

        if (updatedSchedule.getStart_time() != null && updatedSchedule.getEnd_time() != null && updatedSchedule.getStart_time().isAfter(updatedSchedule.getEnd_time()))
            throw new ApiException("Start time must be before end time");


        if (updatedSchedule.getDay() != null)
            schedule.setDay(updatedSchedule.getDay());
        if (updatedSchedule.getStart_date() != null)
            schedule.setStart_date(updatedSchedule.getStart_date());
        if (updatedSchedule.getEnd_date() != null)
            schedule.setEnd_date(updatedSchedule.getEnd_date());
        if (updatedSchedule.getStart_time() != null)
            schedule.setStart_time(updatedSchedule.getStart_time());
        if (updatedSchedule.getEnd_time() != null)
            schedule.setEnd_time(updatedSchedule.getEnd_time());

        courseScheduleRepository.save(schedule);
    }


    public void deleteSchedule(Integer id, Integer teacherId) {
        CourseSchedule schedule = courseScheduleRepository.findCourseScheduleById(id);
        if (schedule == null)
            throw new ApiException("Schedule not found");

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        if (!schedule.getCourse().getTeacher().equals(teacher))
            throw new ApiException("You are not the teacher for this course");

        courseScheduleRepository.delete(schedule);
    }

    public boolean isOverlapping(CourseSchedule existing, CourseSchedule newSchedule) {
        if (!existing.getDay().equalsIgnoreCase(newSchedule.getDay())) {
            return false;
        }
        boolean dateOverlaps =
                !(newSchedule.getEnd_date().isBefore(existing.getStart_date()) ||
                        newSchedule.getStart_date().isAfter(existing.getEnd_date()));

        if (!dateOverlaps) return false;
        boolean timeOverlaps =
                newSchedule.getStart_time().isBefore(existing.getEnd_time()) &&
                        existing.getStart_time().isBefore(newSchedule.getEnd_time());
        return timeOverlaps;
    }

}
