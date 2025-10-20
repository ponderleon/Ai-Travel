package de.xxcd.aitravel.config;

import de.xxcd.aitravel.assistant.AiTravelAssistant;
import de.xxcd.aitravel.memory.AiTravelChatMemoryStore;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class LangChain4jConfig {

    // TODO 需要配置MCP（时间、高德）、流失输出、自定义创建AiTravelAssistant等

    /**
     * 创建高德地图MCP传输
     * @return  McpTransport
     */
    @Bean
    public McpTransport amapMapsMcpTransport(){

        return new StdioMcpTransport.Builder()
                .command(List.of("npx","-y","@amap/amap-maps-mcp-server")) // 设置高德地图MCP服务器命令
                .environment(Map.of("AMAP_MAPS_API_KEY",System.getenv("AMAP_MAPS_API_KEY"))) // 设置高德地图API-KEY环境变量
                .build(); // 创建高德地图MCP传输

    }

    /**
     * 创建时间MCP传输
     * @return McpTransport
     */
    @Bean
    public McpTransport timeMcpTransport(){

        return new StdioMcpTransport.Builder() // 使用标准输入输出作为传输
                .command(List.of("uvx","mcp-server-time","--local-timezone=Asia/Shanghai")) // 设置时间MCP服务器命令
                .build();
    }

    /**
     *  创建高德地图MCP客户端
     * @return McpClient
     */
    @Bean
    public McpClient amapMapsMcpClient(McpTransport amapMapsMcpTransport){
        return new DefaultMcpClient.Builder()
                .clientName("AmapMapsMcpClient") // 设置客户端名称
                .clientVersion("1.0.0") // 设置客户端版本
                .transport(amapMapsMcpTransport) // 设置传输
                .build(); // 创建高德地图MCP客户端
    }

    /**
     * 创建时间MCP客户端
     * @param timeMcpTransport 时间MCP传输
     * @return McpClient
     */
    @Bean
    public McpClient timeMcpClient(McpTransport timeMcpTransport){
        return new DefaultMcpClient.Builder()
                .clientName("TimeMcpClient") // 设置客户端名称
                .clientVersion("1.0.0") // 设置客户端版本
                .transport(timeMcpTransport) // 设置传输
                .build(); // 创建时间MCP客户端
    }


    /**
     *  创建MCP工具提供者
     * @param amapMapsMcpClient 高德地图MCP客户端
     * @param timeMcpClient 时间MCP客户端
     * @return ToolProvider
     */
    @Bean
    public ToolProvider mcpToolProvider(McpClient amapMapsMcpClient, McpClient timeMcpClient){

        return McpToolProvider.builder()
                .mcpClients(amapMapsMcpClient,timeMcpClient)
                .build();

    }


    /**
     *  配置聊天记忆提供者，使用自定义的AiTravelChatMemoryStore进行消息持久化
     * @param aiTravelChatMemoryStore 自定义的聊天记忆存储
     * @return ChatMemoryProvider
     */
    @Bean
    public ChatMemoryProvider aiTravelChatMemoryProvider(AiTravelChatMemoryStore aiTravelChatMemoryStore){

        return memoryId -> MessageWindowChatMemory.builder() // 使用消息窗口记忆
                .chatMemoryStore(aiTravelChatMemoryStore) // 设置自定义的聊天记忆存储
                .maxMessages(30) // 设置最大消息数为30
                .id(memoryId) // 设置记忆ID
                .build();

    }



    /**
     * 配置Pinecone向量存储
     * @param embeddingModel 向量模型
     * @return EmbeddingStore<TextSegment>
     */
    @Bean
    public EmbeddingStore<TextSegment> pineconeEmbeddingStore(EmbeddingModel embeddingModel){

        return PineconeEmbeddingStore.builder()
                .apiKey(System.getenv("PINECONE_API_KEY")) // 获取Pinecone API-KEY
                .index("ai-travel") // 设置索引名称
                .nameSpace("knowledge-library") // 设置命名空间
                .createIndex(PineconeServerlessIndexConfig.builder() // 如果索引不存在则创建
                        .cloud("AWS") // 设置云服务商
                        .region("us-east-1") // 设置区域
                        .dimension(embeddingModel.dimension()) // 设置向量纬度
                        .build()) // 创建索引配置
                .build(); // 创建PineconeEmbeddingStore

    }

    /**
     *  创建AiTravelAssistant AI助手实例
     * @param pineconeEmbeddingStore Pinecone向量存储
     * @param aiTravelChatMemoryProvider 聊天记忆提供者
     * @param openAiStreamingChatModel OpenAI流式聊天模型
     * @return
     */
    @Bean
    public AiTravelAssistant aiTravelAssistant(EmbeddingStore<TextSegment> pineconeEmbeddingStore,
                                               ChatMemoryProvider aiTravelChatMemoryProvider,
                                               OpenAiStreamingChatModel openAiStreamingChatModel,
                                               ToolProvider mcpToolProvider){
        return AiServices.builder(AiTravelAssistant.class)  // 创建AiTravelAssistant服务
                .chatMemoryProvider(aiTravelChatMemoryProvider) // 设置聊天记忆提供者
                .streamingChatModel(openAiStreamingChatModel) // 设置流式聊天模型
                .contentRetriever(EmbeddingStoreContentRetriever.builder() // 使用向量存储内容检索器
                        .embeddingStore(pineconeEmbeddingStore) // 设置向量存储
                        .build()) // 设置内容检索器
                .toolProvider(mcpToolProvider) // 设置工具提供者
                .build();

    }

}
