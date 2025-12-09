package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Course;
import org.example.labeebsystem.Model.CourseSchedule;
import org.example.labeebsystem.Repository.CourseRepository;
import org.example.labeebsystem.Repository.CourseScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseScheduleService {

    private final CourseRepository courseRepository;
    private final CourseScheduleRepository courseScheduleRepository;


    public List<CourseSchedule> getAllSchedules() {
        return courseScheduleRepository.findAll();
    }

    public void addSchedule(Integer courseId, CourseSchedule schedule) {

        Course course = courseRepository.findCourseById(courseId);
        if (course == null)
            throw new ApiException("Course not found");

        if (course.getCourseSchedule() != null)
            throw new ApiException("This course already has a schedule");


        if (schedule.getStart_date().isAfter(schedule.getEnd_date()))
            throw new ApiException("Start date must be before end date");

        if (schedule.getStart_time().isAfter(schedule.getEnd_time()))
            throw new ApiException("Start time must be before end time");

        schedule.setCourse(course);
        courseScheduleRepository.save(schedule);
    }



    public CourseSchedule getScheduleById(Integer id) {
        CourseSchedule schedule = courseScheduleRepository.findCourseScheduleById(id);
        if (schedule == null)
            throw new ApiException("Schedule not found");
        return schedule;
    }



    public void updateSchedule(Integer id, CourseSchedule updatedSchedule) {

        CourseSchedule schedule = courseScheduleRepository.findCourseScheduleById(id);
        if (schedule == null)
            throw new ApiException("Schedule not found");


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


    public void deleteSchedule(Integer id) {
        CourseSchedule schedule = courseScheduleRepository.findCourseScheduleById(id);
        if (schedule == null)
            throw new ApiException("Schedule not found");

        courseScheduleRepository.delete(schedule);
    }
}
