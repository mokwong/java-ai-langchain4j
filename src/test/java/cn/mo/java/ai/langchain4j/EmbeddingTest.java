package cn.mo.java.ai.langchain4j;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModelFactory;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mo
 * @Description 向量模型
 * @createTime 2025年07月11日 17:25
 */
@SpringBootTest
public class EmbeddingTest {

    // 不申请 api 了，使用内置的向量模型验证一下吧
    private EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModelFactory().create();

    @Test
    public void testEmbeddingModel() {
        Response<Embedding> embed = embeddingModel.embed("你好");
        // 384
        System.out.println("向量维度：" + embed.content().dimension());
        System.out.println("向量输出：" + embed);
    }

    @Autowired
    private EmbeddingStore embeddingStore;

    /**
     * 将文本转换成向量，然后存储到内存中
     * <p>
     * 参考：
     * https://docs.langchain4j.dev/tutorials/embedding-stores
     */
    @Test
    public void testPineconeEmbeded() {
        // 将文本转换成向量
        TextSegment segment1 = TextSegment.from("我喜欢羽毛球");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        // 存入向量数据库
        embeddingStore.add(embedding1, segment1);
        TextSegment segment2 = TextSegment.from("今天天气很好");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(embedding2, segment2);
        // debug 查看
        System.out.println(embeddingStore);
    }

    /**
     * Pinecone-相似度匹配
     */
    @Test
    public void embeddingSearch() {
        // 提问，并将问题转成向量数据
        Embedding queryEmbedding = embeddingModel.embed("你最喜欢的运动是什么？").content();
        // 创建搜索请求对象
        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1) //匹配最相似的一条记录 top-k = 1
                .minScore(0.8) //最小匹配得分
                .build();
        //  根据搜索请求 searchRequest 在向量存储中进行相似度搜索
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(searchRequest);
        // searchResult.matches()：获取搜索结果中的匹配项列表。
        // .get(0)：从匹配项列表中获取第一个匹配项
        EmbeddingMatch<TextSegment> embeddingMatch = searchResult.matches().get(0);
        // 获取匹配项的相似度得分
        System.out.println(embeddingMatch.score()); // 0.8144288515898701
        // 返回文本结果
        System.out.println(embeddingMatch.embedded().text());
    }

}
