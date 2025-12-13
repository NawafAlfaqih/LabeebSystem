package org.example.labeebsystem.DTO_in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnglishAssessmentResultDTO {
    private Integer student_id;
    private String transcript;
    private String english_level;
    private String strengths;
    private String weaknesses;
    private String overall_assessment;
}

