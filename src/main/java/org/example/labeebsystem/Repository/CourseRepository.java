package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Course;
import org.example.labeebsystem.Model.CourseSchedule;
import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findCourseById(Integer id);

    Course findCourseByStudentAndTeacher(Student student, Teacher teacher);

}
