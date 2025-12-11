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

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "URL must be added")
    private String session_url;

    @NotEmpty(message = "Date must be filled")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate date;


    @Pattern(regexp = "^(Attended|Absent|Late)$")
    private String attendance;


    //هذي عشان يرفع العذر
    private String excuseText;

    // هنا يرفع ملف العذر
    private String excuseFileUrl;


    @ManyToOne
    @JsonIgnore
    private Student student;

    //if we needed teacher in service
//    @ManyToOne
//    @JsonIgnore
//    private Teacher teacher;

    @ManyToOne
    @JsonIgnore
    private CourseSchedule courseSchedule;

}