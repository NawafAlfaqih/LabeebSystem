package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGPTService {
    private final OpenAiChatModel chatModel;

    public String askChatGPT(String promptText) {
        UserMessage message = new UserMessage(promptText);
        Prompt prompt = new Prompt(message);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}