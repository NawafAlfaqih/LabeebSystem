package org.example.labeebsystem.Service;


import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.DTO_in.EnglishAssessmentResultDTO;
import org.example.labeebsystem.DTO_out.EnglishAssessmentRequestDTO;
import org.example.labeebsystem.Model.Admin;
import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Model.StudentAssessment;
import org.example.labeebsystem.Repository.AdminRepository;
import org.example.labeebsystem.Repository.StudentAssessmentRepository;
import org.example.labeebsystem.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAssessmentService {
    private final N8nEnglishAssessmentService n8nEnglishAssessmentService;
    private final StudentAssessmentRepository studentAssessmentRepository;
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;

    public List<StudentAssessment> getAllAssessments(Integer adminId){
        Admin admin=adminRepository.findAdminById(adminId);
        if(admin==null)
            throw new ApiException("admin ID not found");
        return studentAssessmentRepository.findAll();
    }

    public void addStudentAssessment(EnglishAssessmentRequestDTO englishAssessmentRequestDTO){
        Student student=studentRepository.findStudentById(englishAssessmentRequestDTO.getStudentId());
        if(student==null)
            throw new ApiException("student ID not found");
        EnglishAssessmentResultDTO resultDTO = n8nEnglishAssessmentService.triggerAssessment(englishAssessmentRequestDTO);
        StudentAssessment studentAssessment=new StudentAssessment(
                null,resultDTO.getTranscript()
                ,resultDTO.getEnglish_level(),resultDTO.getStrengths(),
                resultDTO.getWeaknesses(),resultDTO.getOverall_assessment(),student);
        studentAssessmentRepository.save(studentAssessment);
    }

    public void deleteStudentAssessment(Integer studentId,Integer assessmentId){
        Student student=studentRepository.findStudentById(studentId);
        if(student==null)
            throw new ApiException("student ID not found");
        StudentAssessment assessment =  studentAssessmentRepository.findStudentAssessmentById(assessmentId);
        if(assessment==null)
            throw new ApiException("Assessment not found");
        if(!(assessment.getStudent().getId().equals(studentId)))
            throw new ApiException("this assessment does not belongs to you");
        studentAssessmentRepository.delete(assessment);
    }

}
