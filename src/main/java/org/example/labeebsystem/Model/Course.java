package org.example.labeebsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Course title must be filled")
    private String title;


    private Double price;

    @NotEmpty(message = "description must be filled")
    private String description;

    @ManyToOne
    @JsonIgnore
    private Teacher teacher;

    @ManyToOne
    @JsonIgnore
    private Student student;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Set<Session> sessions;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "course")
    @PrimaryKeyJoinColumn
    private CourseSchedule courseSchedule;
}

