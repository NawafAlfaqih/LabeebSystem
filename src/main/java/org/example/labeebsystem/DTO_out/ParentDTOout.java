package org.example.labeebsystem.DTO_out;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.labeebsystem.Model.Student;

import java.util.Set;

@Data
@AllArgsConstructor
public class ParentDTOout {

    private Integer id;

    private String name;

    private String phoneNumber;

    private Integer balance;

    private String email;

    private Set<Student> students;
}
