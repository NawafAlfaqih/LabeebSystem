package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Course;
import org.example.labeebsystem.Model.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findCourseById(Integer id);

}
