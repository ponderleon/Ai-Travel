package de.xxcd.aitravel.config;

import de.xxcd.aitravel.store.AiTravelChatMemoryStore;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.image.ImageModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChain4jConfig {


    /**
     * 配置消息持久化
     * @return ChatMemory
     */
    @Bean
    public ChatMemory chatMemory(){

        // 设置消息窗口最大消息数为100
        return MessageWindowChatMemory.withMaxMessages(100);

    }

    ///**
    // * 手动创建ChatModelMemoryAssistant，并设置chatMemory和openAiChatModel
    // * @param chatMemory 记忆对象
    // * @param openAiChatModel 模型对象
    // * @return ChatModelMemoryAssistant
    // */
    //@Bean
    //public ChatModelMemoryAssistant chatModelMemoryAssistant(ChatMemory chatMemory, OpenAiChatModel openAiChatModel){
    //
    //    return AiServices.builder(ChatModelMemoryAssistant.class)
    //            .chatMemory(chatMemory)
    //            .chatModel(openAiChatModel)
    //            .build();
    //
    //}


    /**
     * 配置通义千文图像生成模型
     * @return ImageModel
     */
    @Bean
    public ImageModel wanxImageModel(){

        return WanxImageModel.builder()
                .apiKey(System.getenv("DASH_SCOPE_API_KEY")) // 获取API-KEY
                .modelName("qwen-image-plus") // 设置模型名称
                .baseUrl("https://dashscope.aliyuncs.com/api/v1") // 设置API请求的基础URL
                .build();

    }




    /**
     * 配置ChatMemoryProvider，用于聊天记忆隔离
     * @return ChatMemoryProvider
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        /**
         *
         * 使用Lambda表达式实现ChatMemoryProvider接口
         *
         * (memoryId) - > return MessageWindowChatMemory.builder().id(memoryId).maxMessages(100).build();
         *
         *
         */
        return memoryId -> MessageWindowChatMemory.builder().id(memoryId).maxMessages(100).build();
    }

    /**
     * 配置ChatMemoryProvider使用自定义的消息存储
     * @param chatMemoryStore 自定义消息存储
     * @return ChatMemoryProvider
     */
    @Bean
    public ChatMemoryProvider chatMemoryProviderCustom(AiTravelChatMemoryStore chatMemoryStore){

        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId) // 设置记忆ID
                .maxMessages(100) // 设置最大消息数
                .chatMemoryStore(chatMemoryStore) // 设置消息存储
                .build();


    }

}
