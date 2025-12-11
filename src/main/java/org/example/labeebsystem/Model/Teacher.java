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

    @Column(columnDefinition = "varchar(15) not null")
    private String name;

    @Column(columnDefinition = "varchar(40) not null unique")
    private String email;

    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @Column(columnDefinition = "varchar(20) not null")
    private String major;

    @Column(columnDefinition = "TEXT not null")
    private String bio;

    @Column(columnDefinition = "double default 0")
    @PositiveOrZero(message = "balance must be positive or zero")
    private Double balance;

    @Column(columnDefinition = "int default 0")
    @PositiveOrZero(message = "rating must be positive or zero")
    @Max(value = 5)
    private Integer rating;

    @Column(columnDefinition = "varchar(15) not null")
    private String activeStatus;

    @ManyToOne
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
