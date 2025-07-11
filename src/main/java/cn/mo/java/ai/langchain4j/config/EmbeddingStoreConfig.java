package cn.mo.java.ai.langchain4j.config;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModelFactory;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mo
 * @Description 配置向量存储对象
 * @createTime 2025年07月11日 17:37
 */
@Configuration
public class EmbeddingStoreConfig {

    // 不申请 api 了，使用内置的向量模型验证一下吧
    private EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModelFactory().create();

    // 不使用 Pinecone 了，使用内存的向量存储验证一下吧
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        //创建向量存储
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        TextSegment segment1 = TextSegment.from("我喜欢羽毛球");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        // 存入向量数据库
        embeddingStore.add(embedding1, segment1);
        TextSegment segment2 = TextSegment.from("今天天气很好");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(embedding2, segment2);
        return embeddingStore;
    }

}
