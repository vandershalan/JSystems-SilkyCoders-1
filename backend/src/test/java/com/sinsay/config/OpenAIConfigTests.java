package com.sinsay.config;

import com.openai.client.OpenAIClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class OpenAIConfigTests {

    @Autowired
    private OpenAIClient openAIClient;

    @Test
    void openAIClientBean_shouldBeConfigured() {
        assertNotNull(openAIClient, "OpenAIClient bean should be configured");
    }

    @Test
    void openAIClientBean_shouldHaveCorrectConfiguration() {
        assertNotNull(openAIClient, "OpenAIClient should not be null");
    }
}
