package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.DTO_out.EnglishAssessmentRequestDTO;
import org.example.labeebsystem.DTO_in.EnglishAssessmentResultDTO;
import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
@RequiredArgsConstructor
public class N8nEnglishAssessmentService {
    private final RestTemplate restTemplate = new RestTemplate();
    public EnglishAssessmentResultDTO triggerAssessment(EnglishAssessmentRequestDTO dto) {
        String url = "https://d7-ak.app.n8n.cloud/webhook/english_assessment";
        EnglishAssessmentResultDTO result = restTemplate.postForObject(url,dto,EnglishAssessmentResultDTO.class);
        return result;
    }
}


