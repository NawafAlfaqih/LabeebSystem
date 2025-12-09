package org.example.labeebsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "start Date must be filled")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate start_date;

    @NotEmpty(message = "end Date must be filled")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate end_date;

    @NotEmpty(message = "start time must be filled")
    @Column(columnDefinition = "time not null")
    private LocalTime start_time;

    @NotEmpty(message = "end time must be filled")
    @Column(columnDefinition = "time not null")
    private LocalTime end_time;

    @Pattern(regexp = "^(sunday|monday|tuesday|wednesday|thursday|friday|saturday)$",message = "day must be 'sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday' or 'saturday'")
    private String day;//week days

    @OneToOne
    @MapsId
    @JsonIgnore
    private Course course;
}