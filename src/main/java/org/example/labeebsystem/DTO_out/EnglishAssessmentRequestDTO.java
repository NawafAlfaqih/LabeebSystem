package org.example.labeebsystem.DTO_out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnglishAssessmentRequestDTO {
    private Integer studentId;
    private String fileUrl;
}

