package org.example.labeebsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Service.ChatGPTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gpt")
@RequiredArgsConstructor
public class TestGpt {
    private final ChatGPTService chatGPTService;

    @PostMapping("/test/{ask}")
    public ResponseEntity<?> test(@PathVariable String ask){
        String response = chatGPTService.askChatGPT(ask);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }


}
