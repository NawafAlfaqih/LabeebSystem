package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Course;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Repository.CourseRepository;
import org.example.labeebsystem.Repository.CourseScheduleRepository;
import org.example.labeebsystem.Repository.SessionRepository;
import org.example.labeebsystem.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseScheduleRepository courseScheduleRepository;
    private final SessionRepository sessionRepository;



    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void addCourse(Integer teacherId, Course course) {
        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");
        if (!teacher.getCategory().getId().equals(teacher.getCategory().getId()))
            throw new ApiException("Teacher is not qualified to teach this course");
        course.setTeacher(teacher);
        courseRepository.save(course);
    }

    public void updateCourse(Integer courseId, Course updatedCourse) {

        Course course = courseRepository.findCourseById(courseId);
        if (course == null)
            throw new ApiException("Course not found");
        course.setPrice(updatedCourse.getPrice());
        course.setDescription(updatedCourse.getDescription());
        courseRepository.save(course);
    }

    public void deleteCourse(Integer id) {
        Course course = courseRepository.findCourseById(id);
        if (course == null)
            throw new ApiException("Course not found");
        courseRepository.delete(course);
    }

}
