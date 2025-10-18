package de.xxcd.aitravel.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChain4jConfig {

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
}
