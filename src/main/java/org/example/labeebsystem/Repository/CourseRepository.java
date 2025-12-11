package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Course;
import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findCourseById(Integer id);

    Course findCourseByTeacher(Teacher teacher);

}
