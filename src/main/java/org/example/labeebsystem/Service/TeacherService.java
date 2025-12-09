package org.example.labeebsystem.Service;


import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.Model.Teacher;
import org.example.labeebsystem.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;


}
