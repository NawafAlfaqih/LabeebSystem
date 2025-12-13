package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.DTO_in.EnglishAssessmentResultDTO;
import org.example.labeebsystem.DTO_in.UrlDTO;
import org.example.labeebsystem.DTO_out.EnglishAssessmentRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class N8nZoomUrlService {
    private final RestTemplate restTemplate = new RestTemplate();
    public UrlDTO triggerZoom(Integer sessionId) {
        String url = "https://d7-ak.app.n8n.cloud/webhook/zoom-create";
        UrlDTO result = restTemplate.postForObject(url,sessionId,UrlDTO.class);
        return result;
    }
}