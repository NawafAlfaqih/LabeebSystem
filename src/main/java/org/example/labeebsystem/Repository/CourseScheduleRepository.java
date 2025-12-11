package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Course;
import org.example.labeebsystem.Model.CourseSchedule;
import org.example.labeebsystem.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule,Integer> {
    CourseSchedule findCourseScheduleById(Integer id);
    
    CourseSchedule findCourseScheduleByStudentAndCourse(Student student, Course course);
}
