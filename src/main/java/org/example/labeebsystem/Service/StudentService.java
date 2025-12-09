package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Parent;
import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Repository.AdminRepository;
import org.example.labeebsystem.Repository.ParentRepository;
import org.example.labeebsystem.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final ParentRepository parentRepository;

    public List<Student> getAllStudents(Integer adminId) {
        if (adminRepository.findAdminById(adminId) == null)
            throw new ApiException("Admin was not found");

        return studentRepository.findAll();
    }

    public void addStudent(Integer parentId, Student student) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null)
            throw new ApiException("Parent was not found");

        student.setParent(parent);
        student.setBalance(0);
        student.setTotalGrade(0);
        studentRepository.save(student);
    }

    public void updateStudent(Integer id, Integer parentId, Student student) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null)
            throw new ApiException("Parent was not found");

        Student oldStudent = studentRepository.findStudentById(id);
        if (oldStudent == null)
            throw new ApiException("Student was not found");

        if (studentRepository.findStudentByParent(parent) == null)
            throw new ApiException("You can only edit your children associated with your account");

        oldStudent.setName(student.getName());
        oldStudent.setBalance(student.getBalance());
        oldStudent.setTotalGrade(student.getTotalGrade());
        oldStudent.setAge(student.getAge());

        studentRepository.save(oldStudent);
    }

    public void deleteStudent(Integer parentId, Integer id) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null)
            throw new ApiException("Parent was not found");

        Student student = studentRepository.findStudentById(id);
        if (student == null)
            throw new ApiException("Student was not found");

        if (studentRepository.findStudentByParent(parent) == null) //not parent
            throw new ApiException("You can only edit your children associated with your account");

        studentRepository.delete(student);
    }
}
