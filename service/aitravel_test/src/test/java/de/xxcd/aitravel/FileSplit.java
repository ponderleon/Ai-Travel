package de.xxcd.aitravel;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenCountEstimator;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FileSplit {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Test
    public void testFileSplit() {
        // 读取文件
        Document document = FileSystemDocumentLoader.loadDocument("/Users/miniserver/Desktop/knowledge/科室信息.md");
        //  创建内存中的向量存储
        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();

        // 将文档分段，并将分段后的数据保存到向量存储中
        EmbeddingStoreIngestor.ingest(document, store);


        System.out.println(store.serializeToJson());

    }

    @Test
    public void addEmbeddingStore() {
        // 创建简单的内存存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        TextSegment textSegment = TextSegment.from("你好，祝您生活愉快！");

        // 进行向量化，通过使用向量大模型进行向量化
        Embedding embedding = embeddingModel.embed(textSegment).content();
        // 加入到向量存储中
        embeddingStore.add(embedding, textSegment);

        System.out.println(embeddingStore.serializeToJson());

    }

    @Test
    public void queryEmbeddingStore() {
        // 创建简单的内存存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        TextSegment textSegment = TextSegment.from("我喜欢看动漫");

        // 进行向量化，通过使用向量大模型进行向量化
        Embedding embedding = embeddingModel.embed(textSegment).content();

        embeddingStore.add(embedding, textSegment);

        TextSegment textSegment2 = TextSegment.from("我喜欢看电影");

        Embedding embedding2 = embeddingModel.embed(textSegment2).content();

        embeddingStore.add(embedding2, textSegment2);

        // 将查询条件进行向量化
        Embedding searchEmbedding = embeddingModel.embed("动漫").content();
        // 构建向量搜索请求对象
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder() // 创建向量搜索请求
                .queryEmbedding(searchEmbedding) // 设置查询的向量
                .maxResults(1) // 设置最大的返回结果数量
                //.minScore(0.75) // 设置最小相识度分数
                .build(); // 构建向量请求

        // 进行查询
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequest);

        // 获取查询结果
        List<EmbeddingMatch<TextSegment>> matches = searchResult.matches();
        // 获取第一条结果
        EmbeddingMatch<TextSegment> embeddingMatch = matches.get(0);

        // 获取得分
        System.out.println("得分为：" + embeddingMatch.score());

        // 获取文本内容
        System.out.println("文本内容：" + embeddingMatch.embedded());

        // 获取向量坐标
        System.out.println("向量坐标：" + embeddingMatch.embedding());
        // 数据库ID
        System.out.println("数据库ID：" + embeddingMatch.embeddingId());


        System.out.println(matches);

    }


    @Test
    public void testCustomDocumentSplitter() {
        //
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // 读取文档信息
        Document document = FileSystemDocumentLoader.loadDocument("/Users/miniserver/Desktop/knowledge/科室信息.md");

        // 创建自定义的文档分词器
        DocumentSplitter documentSplitter =
                new DocumentByParagraphSplitter(300, 30, new HuggingFaceTokenCountEstimator());

        // 自定义分词器进行分词，并且保存到向量数据库中
        EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build()
                .ingest(document);

        System.out.println(embeddingStore.serializeToJson());

    }


}
