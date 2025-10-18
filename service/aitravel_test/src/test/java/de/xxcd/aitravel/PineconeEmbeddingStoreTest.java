package de.xxcd.aitravel;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PineconeEmbeddingStoreTest {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Test
    public void testPineconeEmbeddingStore() {

        // 创建PineconeEmbeddingStore
        PineconeEmbeddingStore pineconeEmbeddingStore = PineconeEmbeddingStore.builder()
                .apiKey(System.getenv("PINECONE_API_KEY")) // 获取Pinecone API-KEY
                .index("ai-travel") // 设置索引名称
                .nameSpace("knowledge-library") // 设置命名空间
                .createIndex(PineconeServerlessIndexConfig.builder() // 如果所以不存在则创建
                        .cloud("AWS") // 设置云服务商
                        .region("us-east-1") // 设置区域
                        .dimension(embeddingModel.dimension()) // 设置向量纬度
                        .build()) // 创建索引配置
                .build(); // 创建PineconeEmbeddingStore

        // 创建文本数据
        //TextSegment textSegment = TextSegment.from("我喜欢看动漫");

        // 将数据向量化
        //Embedding embedding = embeddingModel.embed(textSegment).content();

        // 测试添加向量
        //pineconeEmbeddingStore.add(embedding,textSegment);

        //TextSegment textSegment2 = TextSegment.from("我喜欢看电影");

        // 测试添加向量，并将文本向量化
        //pineconeEmbeddingStore.add(embeddingModel.embed(textSegment2).content(),textSegment2);

        // 测试相似度搜索

        // 创建向量搜索请求
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding( embeddingModel.embed("动漫").content())
                .maxResults(1)
                .minScore(0.8)
                .build();

        // 执行向量搜索，并获取返回结果
        EmbeddingSearchResult<TextSegment> searchResult = pineconeEmbeddingStore.search(embeddingSearchRequest);

        // 获取匹配的结果列表
        List<EmbeddingMatch<TextSegment>> matchList = searchResult.matches();

        // 获取第一个匹配结果
        EmbeddingMatch<TextSegment> embeddingMatch = matchList.get(0);

        System.out.println("数据库ID" + embeddingMatch.embeddingId());

        System.out.println("向量坐标：" + embeddingMatch.embedding());

        System.out.println("文本信息："+embeddingMatch.embedded());

        System.out.println("相似度分数：" + embeddingMatch.score());


    }
}
