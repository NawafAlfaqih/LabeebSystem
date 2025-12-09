package org.example.labeebsystem.Service;


import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final EmailService emailService;

    public List<Teacher> getAllTeachers(){
        List<Teacher> allTeachers=teacherRepository.findAll();
        if(allTeachers.isEmpty()){
            throw new ApiException("no teachers yet");
        }
        return allTeachers;
    }

    public void addTeacher(Teacher teacher){
        Teacher teacher1 = teacherRepository.findTeacherByEmail(teacher.getEmail());
        if(teacher1!=null){
            throw new ApiException("this email: "+teacher.getEmail()+" ,is already used");
        }
        teacher.setActiveStatus("pending");
        emailService.sendEmail(teacher.getEmail(),"welcome to Labeeb system","to activate your account please provide us with the following Documents :" +
                "\n1. your last degree,2. your passport ");
        teacherRepository.save(teacher);
    }

    public void updateTeacher(Integer id,Teacher teacher){
        Teacher teacher1=teacherRepository.findTeacherById(id);
        if (teacher1 == null) {
            throw new ApiException("teacher id not found") ;
        }
        if (!teacher1.getEmail().equals(teacher.getEmail())) {
            if (teacherRepository.existsByEmail(teacher.getEmail())) {
                throw new ApiException( "Email already exists");
            }
        }
    }

    public void deleteTeacher(Integer id){
        Teacher teacher=teacherRepository.findTeacherById(id);
        if(teacher==null){
            throw new ApiException("teacher ID was not found");
        }
        teacherRepository.delete(teacher);
    }



}
