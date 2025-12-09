package org.example.labeebsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name can't be empty")
    @Size(min = 2,max = 15,message = "name must be 2 litters minimum, 15 litters maximum")
    @Column(columnDefinition = "varchar(15) not null")
    private String name;

    @NotBlank(message = "email cannot be blank.")
    @Column(columnDefinition = "varchar(40) not null unique")
    @Email
    private String email;

    @NotBlank(message = "password cannot be blank.")
    @Size(min = 8, message = "password length must be at least '8'.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,?!])(?=\\S+$).{8,20}$",
            message = "Password must contain at least one digit, one lowercase letter, " +
                    "one uppercase letter, one special character, and be 8-20 characters long.")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotBlank(message = "major cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String major;

    @NotBlank(message = "bio cannot be empty")
    @Column(columnDefinition = "varchar(256) not null")
    private String bio;

    @Column(columnDefinition = "double default 0")
    @PositiveOrZero(message = "balance must be positive or zero")
    private Double balance;

    @Column(columnDefinition = "int default 0")
    @PositiveOrZero(message = "rating must be positive or zero")
    @Max(value = 5)
    private Integer rating;

    @Pattern(regexp = "^(pending|accepted|rejected)$")
    @Column(columnDefinition = "varchar(15) not null")
    private String ActiveStatus;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<TeacherReview> teacherReviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<Course> courses;

    @OneToMany(mappedBy = "teacher")
    private Set<TaskFeedback> taskFeedbacks;

    //if we needed teacher in service
//    @OneToMany(mappedBy = "teacher")
//    private Set<Session> sessions;


}
