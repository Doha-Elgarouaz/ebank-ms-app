package org.example.ebankbot.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatobtController {
    private ChatClient chatClient;

    public EbankChatobtController(ChatClient.Builder chatClient, ChatMemory chatmemory) {
        this.chatClient = chatClient
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatmemory).build())
                .build();
    }
    @GetMapping("/chat")
    public String chat(
            @RequestParam(name = "query", defaultValue = "bonjour") String query,
            @RequestParam(name = "conversationId", defaultValue = "default") String conversationId) {
        return chatClient.prompt(query)
                .advisors(a -> a.param("conversationId", conversationId))
                .call()
                .content();
    }
}
