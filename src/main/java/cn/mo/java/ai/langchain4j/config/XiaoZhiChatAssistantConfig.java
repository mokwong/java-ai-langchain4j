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
     * 用户每次聊天，都会进行检索，而且检索出来的内容其实没什么价值
     * {
     *     "role" : "user",
     *     "content" : "我有点头疼，怎么办\n\nAnswer using the following information:\n出贡献中青年专家”荣誉称号。\n\n　　科室现有医生 57人，其中教授11人、副教授17人、主治医生18人、住院医生11人，其中博士生导师7人，硕士生导师8人。现有各级护理人员50人。科室重视人才培养，老一辈知名专家老当益壮，中青年专家年富力强，年轻医师迅速成长。90%的高级教师有在国外学习和进修的经历。现任科室主任崔丽英教授担任现任中华医学会神经病学分会主任委员（第七届）及《中华神经科杂志》主编，并曾担任中华医学会神经病学分会第四届主任委员，科室20人次在中华医学会各专业学组任职，10余人担任多家国内核心期刊杂志副主编及编委。\n\n## 科室地址\n\n东院门诊：东院门诊楼6层。\n\n## 专家团队\n\n### 彭斌\n\n副院长 主任医师 教授 博士研究生导师 Professor\n\n详细介绍\n朱以诚，女，主任医师，教授，神经内科主任，神经病学系主任，博士研究生导师。"
     *   }
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
