package org.example.labeebsystem.Service;


import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.DTO_out.EnglishAssessmentRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class N8nMarkPredictionService {
    private final RestTemplate restTemplate = new RestTemplate();
    public String triggerPrediction(String answerUrl) {
        String url = "https://d7-ak.app.n8n.cloud/webhook/doc-analyze";
        return restTemplate.postForObject(url,answerUrl,String.class);
    }
}