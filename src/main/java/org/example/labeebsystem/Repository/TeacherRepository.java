package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Category;
import org.example.labeebsystem.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Teacher findTeacherById(Integer id);

    Teacher findTeacherByEmail(String email);

    Boolean existsByEmail(String email);

    List<Teacher> getTeachersByCategory(Category category);

    @Query("select t from Teacher t order by t.rating asc ")
    List<Teacher> getTeachersOrderedByRating();
}
