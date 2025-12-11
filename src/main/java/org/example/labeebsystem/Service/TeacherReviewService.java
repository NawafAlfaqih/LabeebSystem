package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Parent;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Model.TeacherReview;
import org.example.labeebsystem.Repository.ParentRepository;
import org.example.labeebsystem.Repository.TeacherRepository;
import org.example.labeebsystem.Repository.TeacherReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeacherReviewService {
    private final TeacherReviewRepository teacherReviewRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;


    public List<TeacherReview> getAllTeacherReviews(){
        List<TeacherReview> allReviews=teacherReviewRepository.findAll();
        if(allReviews.isEmpty()){
            throw new ApiException("no reviews yet");
        }
        return allReviews;
    }

    // Fix: when adding new review, check the active status (if accepted).
    // and updates the teachers Rating
    public void addTeacherReview(Integer parentId, Integer teacherId, TeacherReview teacherReview){
        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        Parent parent=parentRepository.findParentById(parentId);
        if(teacher==null||parent==null){
            throw new ApiException("teacher or parent ID was not found");
        }
        if(!(teacher.getActiveStatus().equalsIgnoreCase("accepted"))){
            throw new ApiException("teacher is not Active");
        }

        if (teacherReviewRepository.findTeacherReviewByTeacherAndParent(teacher, parent) != null)
            throw new ApiException("Can't review the same teacher twice.");

        teacherReview.setTeacher(teacher);
        teacherReview.setParent(parent);
        teacherReview.setCreatedAt(LocalDateTime.now());
        teacherReviewRepository.save(teacherReview);

        teacher.getTeacherReviews().add(teacherReview);
        Set<TeacherReview> reviews = teacher.getTeacherReviews();
        int total = reviews.stream()
                .mapToInt(TeacherReview::getRating)
                .sum();
        int avg = total / reviews.size();
        teacher.setRating(avg);
        teacherRepository.save(teacher);
    }

    public void updateTeacherReview(Integer teacherReviewID,TeacherReview teacherReview){
        TeacherReview oldTeacherReview=teacherReviewRepository.findTeacherReviewById(teacherReviewID);
        if(oldTeacherReview==null){
            throw new ApiException("teacher review ID was not found");
        }
        oldTeacherReview.setRating(teacherReview.getRating());
        oldTeacherReview.setComment(teacherReview.getComment());
        teacherReviewRepository.save(oldTeacherReview);

        Teacher teacher = oldTeacherReview.getTeacher();

        Set<TeacherReview> reviews = teacher.getTeacherReviews();

        if (reviews.isEmpty()) {
            teacher.setRating(0);
        } else {
            int total = reviews.stream().mapToInt(TeacherReview::getRating).sum();
            int avg = total / reviews.size();
            teacher.setRating(avg);
        }

        teacherRepository.save(teacher);
    }

    public void deleteTeacherReview(Integer teacherReviewID){
        TeacherReview oldTeacherReview=teacherReviewRepository.findTeacherReviewById(teacherReviewID);
        if(oldTeacherReview==null){
            throw new ApiException("teacher review ID was not found");
        }
        teacherReviewRepository.delete(oldTeacherReview);

        Teacher teacher = oldTeacherReview.getTeacher();
        teacher.getTeacherReviews().remove(oldTeacherReview);
        Set<TeacherReview> reviews = teacher.getTeacherReviews();

        if (reviews.isEmpty()) {
            teacher.setRating(0);
        } else {
            int total = reviews.stream().mapToInt(TeacherReview::getRating).sum();
            int avg = total / reviews.size();
            teacher.setRating(avg);
        }

        teacherRepository.save(teacher);
    }




}
