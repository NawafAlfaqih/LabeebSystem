package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Model.Task;
import org.example.labeebsystem.Model.TaskFeedback;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Repository.TaskFeedbackRepository;
import org.example.labeebsystem.Repository.TaskRepository;
import org.example.labeebsystem.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskFeedbackService {

    private final TaskFeedbackRepository taskFeedbackRepository;
    private final TaskRepository taskRepository;
    private final TeacherRepository teacherRepository;
    private final N8nMarkPredictionService n8nMarkPredictionService;


    public List<TaskFeedback> getAllFeedbacks() {
        return taskFeedbackRepository.findAll();
    }

    public void addFeedback(Integer taskId, Integer teacherId, TaskFeedback feedback) {

        Task task = taskRepository.findTaskById(taskId);
        if (task == null)
            throw new ApiException("Task not found");

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        Student student = task.getStudent();
        if (student == null)
            throw new ApiException("Task has no assigned student");

        feedback.setTask(task);
        feedback.setTeacher(teacher);
        feedback.setStudent(student);
        feedback.setCreatedAt(LocalDateTime.now());

        taskFeedbackRepository.save(feedback);
    }

    public void updateFeedback(Integer feedbackId, Integer teacherId, TaskFeedback updatedFeedback) {

        TaskFeedback feedback = taskFeedbackRepository.findTaskFeedbackById(feedbackId);
        if (feedback == null)
            throw new ApiException("Feedback not found");


        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        if (!feedback.getTeacher().equals(teacher))
            throw new ApiException("Teacher is not allowed to update this feedback");

        if (updatedFeedback.getComment() != null)
            feedback.setComment(updatedFeedback.getComment());

        taskFeedbackRepository.save(feedback);
    }

    public void deleteFeedback(Integer feedbackId, Integer teacherId) {

        TaskFeedback feedback = taskFeedbackRepository.findTaskFeedbackById(feedbackId);
        if (feedback == null)
            throw new ApiException("Feedback not found");

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");

        if (!feedback.getTeacher().equals(teacher))
            throw new ApiException("Teacher cannot delete this feedback");

        taskFeedbackRepository.delete(feedback);
    }

    public String predictGrade(Integer teacherId,Integer taskId){
        Task task = taskRepository.findTaskById(taskId);
        if (task == null)
            throw new ApiException("Task not found");

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher == null)
            throw new ApiException("Teacher not found");
        return n8nMarkPredictionService.triggerPrediction(task.getFileAnswerUrl());
    }

}
