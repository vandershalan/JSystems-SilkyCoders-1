package com.sinsay.service;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.sinsay.model.ChatMessage;
import com.sinsay.model.Intent;
import com.sinsay.model.Role;
import com.sinsay.model.Session;
import com.sinsay.repository.ChatMessageRepository;
import com.sinsay.repository.SessionRepository;
import com.sinsay.service.dto.AnalysisResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisService {

    private final OpenAIClient openAIClient;
    private final PolicyDocService policyDocService;
    private final SessionRepository sessionRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public AnalysisResponse analyzeAndCreateSession(
            Intent intent,
            String orderNumber,
            String productName,
            String description,
            byte[] imageBytes,
            String mimeType
    ) {
        log.info("Analyzing session: intent={}, order={}, product={}", intent, orderNumber, productName);

        // Convert image to base64 data URI
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        String dataUri = "data:" + mimeType + ";base64," + base64Image;

        // Get system prompt
        String systemPrompt = policyDocService.getSystemPrompt(intent);

        // For this PoC, we'll use a simpler approach without multimodal content
        // The image is converted to a base64 data URI and mentioned in the user message
        String userMessageWithImage = "[Image: " + dataUri + "]\n\n" + description;

        // Build chat completion request
        // Note: The OpenAI Java SDK's builder pattern requires using the standard addMessage methods
        // For full multimodal support with content parts, we would need to use the raw JSON API
        // For this PoC, we include the image data URI in the text message
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.of("openai/gpt-4o-mini"))
                .addSystemMessage(systemPrompt)
                .addUserMessage(userMessageWithImage)
                .build();

        // Call OpenAI API (synchronous, non-streaming)
        ChatCompletion completion = openAIClient.chat().completions().create(params);

        // Extract assistant response
        String assistantMessage = "";
        if (completion != null && !completion.choices().isEmpty()) {
            assistantMessage = completion.choices().get(0).message().content().orElse("");
        }

        // Create and persist session
        Session session = Session.builder()
                .intent(intent)
                .orderNumber(orderNumber)
                .productName(productName)
                .description(description)
                .build();
        session = sessionRepository.save(session);
        UUID sessionId = session.getId();

        // Persist USER message (sequence 0)
        ChatMessage userMessage = ChatMessage.builder()
                .sessionId(sessionId)
                .role(Role.USER)
                .content(description)
                .sequenceNumber(0)
                .build();
        chatMessageRepository.save(userMessage);

        // Persist ASSISTANT message (sequence 1)
        ChatMessage assistantMsgEntity = ChatMessage.builder()
                .sessionId(sessionId)
                .role(Role.ASSISTANT)
                .content(assistantMessage)
                .sequenceNumber(1)
                .build();
        chatMessageRepository.save(assistantMsgEntity);

        log.info("Analysis complete: sessionId={}", sessionId);
        return new AnalysisResponse(sessionId, assistantMessage);
    }
}
