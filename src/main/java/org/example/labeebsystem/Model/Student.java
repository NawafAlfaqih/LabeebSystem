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
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name cannot be empty")
    @Pattern(regexp = "^[\\p{L} ]{2,}$", message = "name must contain characters only")
    private String name;

    @PositiveOrZero(message = "Balance must be zero or positive")
    @Column(columnDefinition = "double default 0")
    private Double balance;

    @Min(value = 0, message = "Total grade must be 0 or more")
    @Max(value = 100, message = "Total grade cannot be more 100")
    @Column(columnDefinition = "int default 0")
    private Integer totalGrade; // leaderboard in one subject

    @Positive
    private Integer age;

    @ManyToOne
    @JsonIgnore
    private Parent parent;

    @OneToMany(mappedBy = "student")
    private Set<Task> tasks;

    @OneToMany(mappedBy = "student")
    private Set<CourseSchedule> courseSchedules;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<TaskFeedback> taskFeedbacks;

    @OneToMany(mappedBy = "student")
    private Set<Session> sessions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<GiftCard> giftCards;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "student")
    private Set<StudentAssessment> studentAssessments;
}
