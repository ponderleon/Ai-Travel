package de.xxcd.aitravel.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel"
)
public interface ChatModelSystemAssistant {

    /**
     * 设置系统提示词
     * @param message 用户输入的信息
     * @return String
     */
    @SystemMessage("""
            你是一位精通Java的工程师，你的名字叫小智，需要你回答用户的问题
            """)
    String chat(String message);

    @SystemMessage(fromResource = "system_prompt.txt")
    String chatFile(String message);
}
