package org.example.labeebsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Start date must be filled")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate start_date;

    @NotNull(message = "End date must be filled")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate end_date;

    @NotNull(message = "Start time must be filled")
    @Column(columnDefinition = "time not null")
    private LocalTime start_time;

    @NotNull(message = "End time must be filled")
    @Column(columnDefinition = "time not null")
    private LocalTime end_time;

    @Pattern(regexp = "^(sunday|monday|tuesday|wednesday|thursday|friday|saturday)$"
            ,message = "day must be 'sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday' or 'saturday'")
    private String day;

    @Pattern(regexp = "^(Available|Registered)$")
    @Column(columnDefinition = "varchar(15) not null")
    private String Availability;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "courseSchedule")
    @PrimaryKeyJoinColumn
    private StudentPayment studentPayment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseSchedule")
    private Set<Session> sessions;

    @ManyToOne
    @JsonIgnore
    private Student student;

    @ManyToOne
    @JsonIgnore
    private Course course;
}
