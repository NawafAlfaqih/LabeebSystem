package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class QuizService {

    private final ChatGPTService chatGPTService;

    private boolean gameStarted = false;
    private boolean levelChosen = false;

    public String playQuiz(String message) {

        message = message.toLowerCase().trim();

        if (!gameStarted) {
            if (message.equals("start")) {
                gameStarted = true;
                return """
                        Choose difficulty:
                        EASY
                        MEDIUM
                        HARD
                        """;
            }
            return "Please say \"start\" to begin the quiz";
        }

        if (!levelChosen) {
            if (message.equals("easy") || message.equals("medium") || message.equals("hard")) {
                levelChosen = true;
                String prompt = """
                Start a quiz with %s difficulty.
                Ask ONE question only.
                Use either:
                - Multiple choice (a, b, c, d)
                - Or True / False
                Do not ask for difficulty again.
                """.formatted(message);

                return chatGPTService.askChatGPT(prompt);
            }
            return "Please choose difficulty: EASY, MEDIUM, or HARD";
        }
        if (message.equals("exit")) {
            gameStarted = false;
            levelChosen = false;
            return "Quiz ended See you next time ";
        }
        String prompt = """
        The user answered: "%s"

        - Say if the answer is correct or wrong
        - Then ask ONE new question
        - Use multiple choice or true/false
        - Do NOT mention start or difficulty
        """.formatted(message);
        return chatGPTService.askChatGPT(prompt);
    }
}
