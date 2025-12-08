package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule,Integer> {
    CourseSchedule findCourseScheduleById(Integer id);
}
