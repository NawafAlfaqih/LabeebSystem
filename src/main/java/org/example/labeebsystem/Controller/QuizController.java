package org.example.labeebsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    //لعبه وقت الفراغ
    @PostMapping("/play")
    public ResponseEntity<?> playQuiz (@RequestBody Map<String, String> body) {

        String message = body.get("message");
        String result = quizService.playQuiz(message);
        return ResponseEntity.status(200).body(new ApiResponse(result));
    }
}
