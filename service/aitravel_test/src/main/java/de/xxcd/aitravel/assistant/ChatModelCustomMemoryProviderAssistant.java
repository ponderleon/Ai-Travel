package de.xxcd.aitravel.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        chatMemoryProvider = "chatMemoryProviderCustom"
)
public interface ChatModelCustomMemoryProviderAssistant {


    String chat(@MemoryId Long memoryId, @UserMessage String userMessage);

}
