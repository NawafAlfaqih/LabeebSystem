package org.example.labeebsystem.Service;


import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Admin;
import org.example.labeebsystem.Model.Category;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Repository.AdminRepository;
import org.example.labeebsystem.Repository.CategoryRepository;
import org.example.labeebsystem.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final EmailService emailService;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;

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

    public List<Teacher> getTeachersByCategory(Integer categoryId){
        Category category=categoryRepository.findCategoryById(categoryId);
        if(category==null){
            throw new ApiException("category ID not found");
        }
        List<Teacher> teachers=teacherRepository.getTeachersByCategory(category);
        if(teachers.isEmpty()){
            throw new ApiException("no teacher in this category yet");
        }
        List<Teacher> activeTeachers=new ArrayList<>();
        for(Teacher t:teachers){
            if(t.getActiveStatus().equals("accepted")){
                activeTeachers.add(t);
            }
        }
        return activeTeachers;
    }

    public List<Teacher> getTeachersOrderedByRating(){
        return teacherRepository.getTeachersOrderedByRating();
    }

    public List<Teacher> getAllPendingTeachers(Integer adminId){
        Admin admin=adminRepository.findAdminById(adminId);
        if(admin==null){
            throw new ApiException("admin ID not found");
        }
        return teacherRepository.getAllPendingTeachers();
    }

    public void acceptTeacher(Integer adminId,Integer teacherId){
        Admin admin=adminRepository.findAdminById(adminId);
        if(admin==null){
            throw new ApiException("admin ID not found");
        }
        Teacher teacher= teacherRepository.findTeacherById(teacherId);
        if(teacher==null){
            throw new ApiException("teacher ID not found");
        }
        if(teacher.getActiveStatus().equalsIgnoreCase("rejected")){
            throw new ApiException("status rejected can't change to accepted");
        }
        if (teacher.getActiveStatus().equalsIgnoreCase("accepted")){
            throw new ApiException("status is already accepted");
        }
            teacher.setActiveStatus("accepted");
            teacherRepository.save(teacher);
            emailService.sendEmail(teacher.getEmail(),"welcome to Labeeb","Dear "+teacher.getName()+ """
                    , We are pleased to welcome you to Labeeb. Your acceptance reflects our confidence in your expertise and the value you will bring to our learners. We look forward to supporting your teaching journey and seeing the positive impact you will make on our community.
                    
                    If you need any assistance as you get started, please feel free to reach out.
                    
                    Welcome aboard,
                    Labeeb Team
                    """);
    }

    public void rejectTeacher(Integer adminId,Integer teacherId){
        Admin admin=adminRepository.findAdminById(adminId);
        if(admin==null){
            throw new ApiException("admin ID not found");
        }
        Teacher teacher= teacherRepository.findTeacherById(teacherId);
        if(teacher==null){
            throw new ApiException("teacher ID not found");
        }
        if(teacher.getActiveStatus().equalsIgnoreCase("rejected")){
            throw new ApiException("teacher is already rejected");
        }
            teacher.setActiveStatus("rejected");
            teacherRepository.save(teacher);
            emailService.sendEmail(teacher.getEmail(),"request to join Labeeb status","Dear "+teacher.getName()+ """
                    Thank you for your interest in joining Labeeb and for the time you invested in your application. After careful review, we are unable to move forward at this time.
                    
                    We appreciate your desire to be part of our platform and encourage you to apply again in the future.
                    
                    Kind regards,
                    Labeeb Team
                    """);
    }




}
