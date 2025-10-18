package de.xxcd.aitravel.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;


/**
 * 如果通过chatMemory实现聊天记忆，则不需要传入MemoryId
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        chatMemory = "chatMemory"
)
public interface ChatModelMemoryAssistant {

    /**
     * 实现聊天记忆
     * @param message 用户输入的信息
     * @return String
     */
    String chat(@UserMessage String message);


}
