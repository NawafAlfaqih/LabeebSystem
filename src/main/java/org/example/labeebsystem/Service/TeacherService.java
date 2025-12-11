package org.example.labeebsystem.Service;


import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.DTO_in.TeacherDTOIN;
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

    public void addTeacher(TeacherDTOIN teacherDTO) {
        if (teacherRepository.findTeacherByEmail(teacherDTO.getEmail()) != null) {
            throw new ApiException("This email is already used: " + teacherDTO.getEmail());
        }
        Category category = categoryRepository.findCategoryByName(teacherDTO.getCategoryType());
        if (category == null)
            throw new ApiException("Category was not found");

        Teacher teacher = new Teacher(
                null,
                teacherDTO.getName(),
                teacherDTO.getEmail(),
                teacherDTO.getPassword(),
                teacherDTO.getMajor(),
                teacherDTO.getBio(),
                0.0,
                0,
                "pending",
                category,
                null,
                null,
                null,
                null
        );

        Teacher savedTeacher = teacherRepository.save(teacher);

        emailService.sendEmail(
                savedTeacher.getEmail(),
                "Welcome to Labeeb system",
                "To activate your account please provide the following documents:\n1. Last degree\n2. Passport"
        );
    }


    public void updateTeacher(Integer id, TeacherDTOIN teacherDTO){
        Teacher teacher1=teacherRepository.findTeacherById(id);
        if (teacher1 == null) {
            throw new ApiException("teacher id not found") ;
        }

        if (!teacher1.getEmail().equals(teacherDTO.getEmail())) {
            if (teacherRepository.existsByEmail(teacherDTO.getEmail())) {
                throw new ApiException( "Email already exists");
            }
        }

        Category category = categoryRepository.findCategoryByName(teacherDTO.getCategoryType());
        if (category == null)
            throw new ApiException("Category was not found");

        teacher1.setName(teacherDTO.getName());
        teacher1.setEmail(teacherDTO.getEmail());
        teacher1.setPassword(teacherDTO.getPassword());
        teacher1.setMajor(teacherDTO.getMajor());
        teacher1.setBio(teacherDTO.getBio());
        teacher1.setCategory(category);

        teacherRepository.save(teacher1);
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
