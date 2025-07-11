package cn.mo.java.ai.langchain4j.config;

import cn.mo.java.ai.langchain4j.mysql.MySqlChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author mo
 * @Description 小智客服 mysql持久化聊天记录
 * @createTime 2025年07月02日 16:49
 */
@Configuration
public class XiaoZhiChatAssistantConfig {

    @Autowired
    private XiaoZhiConfig xiaoZhiConfig;

    /**
     * 这个设计还是很优雅的，ChatMemoryProvider 底层是 MessageWindowChatMemory
     * MessageWindowChatMemory 底层才是 ChatMemoryStore
     * ChatMemoryStore 才是决定如何存储的关键
     *
     * @param mySqlChatMemoryStore
     * @return
     */
    @Bean
    public ChatMemoryProvider xiaoZhiChatMemoryProvider(MySqlChatMemoryStore mySqlChatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(xiaoZhiConfig.getMemorySize().intValue())
                .chatMemoryStore(mySqlChatMemoryStore) // 底层存储到 mysql
                .build();
    }

    /**
     * 小智知识库
     *
     * @return
     */
    @Bean
    public ContentRetriever contentRetrieverXiaozhi() {
        // 使用ClassPathDocumentLoader读取指定目录下的知识库文档
        // 并使用默认的文档解析器对文档进行解析
        Document document1 = ClassPathDocumentLoader.loadDocument("knowledge/医院信息.md");
        Document document2 = ClassPathDocumentLoader.loadDocument("knowledge/科室信息.md");
        Document document3 = ClassPathDocumentLoader.loadDocument("knowledge/神经内科.md");
        List<Document> documents = Arrays.asList(document1, document2, document3);
        // 使用内存向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        // 使用默认的文档分割器
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        // 从嵌入存储（EmbeddingStore）里检索和查询内容相关的信息
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }

}
