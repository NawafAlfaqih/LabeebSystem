package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Parent;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Model.TeacherReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherReviewRepository extends JpaRepository<TeacherReview, Integer> {
    TeacherReview findTeacherReviewById(Integer id);

    TeacherReview findTeacherReviewByTeacherAndParent(Teacher teacher, Parent parent);
}
