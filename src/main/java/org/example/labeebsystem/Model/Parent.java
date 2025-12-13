package org.example.labeebsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name cannot be empty")
    @Pattern(regexp = "^[\\p{L} ]{2,}$", message = "name must contain characters only")
    private String name;

    @NotEmpty(message = "PhoneNumber cannot be empty")
    @Pattern(regexp = "^+966\\d{9}$", message = "PhoneNumber must start with +966 contain 9 digits only")
    private String phoneNumber;

    @Email(message = "Email must be a valid email format")
    @NotEmpty(message = "Email cannot be empty")
    private String email;


    @Column(columnDefinition = "varchar(20)")
    private String discountCode;

    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
            message = "Password must contain uppercase, lowercase, number and be at least 8 characters")
    private String password;

    @PositiveOrZero(message = "Balance must be zero or positive")
    @Column(columnDefinition = "int default 0")
    private Double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<Student> students;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<Task> tasks;

    @OneToMany(mappedBy = "parent")
    private Set<TeacherReview> teacherReviews;


}
