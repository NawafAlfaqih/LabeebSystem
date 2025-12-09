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
import java.util.Set;

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
        studentRepository.save(student);
    }
}
