package de.xxcd.aitravel.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import reactor.core.publisher.Flux;

//@AiService(
//        wiringMode = AiServiceWiringMode.EXPLICIT,
//        streamingChatModel = "openAiStreamingChatModel",
//        chatMemoryProvider = "customChatMemoryProvider",
//        contentRetriever = "pineconeContentRetriever",
//        tools = {"amapMcpToolProvider"}
//)
public interface AiTravelAssistant {

    /**
     * 实现流式输出聊天记忆
     * @param memoryId 聊天记忆ID
     * @param message 用户输入的信息
     * @return Flux<String>
     */
    @SystemMessage(fromResource = "system-prompt-template.txt")
    @UserMessage(fromResource = "user-prompt-template.txt")
    Flux<String> chat(@MemoryId String memoryId, @V("userMessage") String message);

}
