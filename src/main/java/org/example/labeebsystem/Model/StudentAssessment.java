package org.example.labeebsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentAssessment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT not null")
    private String transcript;
    private String english_level;
    @Column(columnDefinition = "TEXT not null")
    private String strengths;
    @Column(columnDefinition = "TEXT not null")
    private String weaknesses;
    @Column(columnDefinition = "TEXT not null")
    private String overall_assessment;

    @ManyToOne
    @JsonIgnore
    private Student student;

}
