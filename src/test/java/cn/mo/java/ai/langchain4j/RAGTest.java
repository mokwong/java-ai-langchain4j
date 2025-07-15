package cn.mo.java.ai.langchain4j;

import dev.langchain4j.community.model.dashscope.QwenTokenCountEstimator;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.document.splitter.recursive.RecursiveDocumentSplitterFactory;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenCountEstimator;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

/**
 * @author mo
 * @Description rag
 * @createTime 2025年07月03日 10:14
 */
@SpringBootTest
public class RAGTest {

    /**
     * 文件系统的文件加载、解析
     */
    @Test
    public void testReadDocument() {
        // 使用 FileSystemDocumentLoader 读取指定目录下的知识库文档
        // 并使用默认的文档解析器 TextDocumentParser 对文档进行解析
        Document document = FileSystemDocumentLoader.loadDocument("D:\\BaiduNetdiskDownload\\课件\\knowledge\\测试.txt");
        System.out.println(document.text());

        // 加载、解析单个文档
        Document document1 = FileSystemDocumentLoader.loadDocument("D:\\BaiduNetdiskDownload\\课件\\knowledge\\测试.txt", new TextDocumentParser());

        // 从一个目录中加载、解析所有文档
        List<Document> documents2 = FileSystemDocumentLoader.loadDocuments("D:\\BaiduNetdiskDownload\\课件\\knowledge", new TextDocumentParser());

        // 从一个目录中加载、解析所有的.txt文档，文件名使用模式匹配过滤
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        List<Document> documents3 = FileSystemDocumentLoader.loadDocuments("D:\\BaiduNetdiskDownload\\课件\\knowledge", pathMatcher, new TextDocumentParser());

        // 递归加载、解析一个目录下的所有文档，从一个目录及其子目录中加载所有文档
        List<Document> documents4 = FileSystemDocumentLoader.loadDocumentsRecursively("D:\\BaiduNetdiskDownload\\课件\\knowledge", new TextDocumentParser());
    }

    /**
     * 类路径下的文件加载、解析
     */
    @Test
    public void testReadDocumentClassPath() {
        Document document = ClassPathDocumentLoader.loadDocument("knowledge\\测试.txt");
        System.out.println(document.text());
//        List<Document> documentList = ClassPathDocumentLoader.loadDocuments("knowledge");
//        for (Document document : documentList) {
//            System.out.println(document.text());
//        }
    }

    /**
     * 加载、解析PDF
     */
    @Test
    public void testParsePDF() {
        Document document = ClassPathDocumentLoader.loadDocument("knowledge/医院信息.pdf", new ApacheTikaDocumentParser());
        System.out.println(document.text());

//        Document document1 = FileSystemDocumentLoader.loadDocument("D:\\zmo\\doc\\books\\reading\\英文\\Cambridge_Vocabulary.pdf", new ApacheTikaDocumentParser());
//        System.out.println(document1.text());
    }

    /**
     * 加载文档并存入向量数据库
     */
    @Test
    public void testReadDocumentAndStore() {
        // 使用 ClassPathDocumentLoader 读取指定目录下的知识库文档
        // 并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = ClassPathDocumentLoader.loadDocument("knowledge/人工智能.md");
        // 为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        // ingest
        // 1、分割文档：默认使用递归分割器，将文档分割为多个文本片段，每个片段包含不超过 300 个 token，并且有 30 个 token 的重叠部分保证连贯性
        // 分割器递归嵌套，段落->行->句子->单词。当一个段落大于300个token，使用行分割器进行二次切割；如果单行长度太长，使用句子分割器三次分割；句子太长，使用单词分割器四次切割
        // DocumentByParagraphSplitter(DocumentByLineSplitter(DocumentBySentenceSplitter(DocumentByWordSplitter)))
        /**
         * @see RecursiveDocumentSplitterFactory#create()
         */
        // 2、文本向量化：使用一个 LangChain4j 内置的轻量化向量模型对每个文本片段进行向量化
        /**
         * @see EmbeddingStoreIngestor#EmbeddingStoreIngestor(dev.langchain4j.data.document.DocumentTransformer, dev.langchain4j.data.document.DocumentSplitter, dev.langchain4j.data.segment.TextSegmentTransformer, dev.langchain4j.model.embedding.EmbeddingModel, dev.langchain4j.store.embedding.EmbeddingStore)
         */
        // 3、将原始文本和向量存储到向量数据库中(InMemoryEmbeddingStore)
        EmbeddingStoreIngestor.ingest(document, embeddingStore);
        // 查看向量数据库内容，每一个文本块，都转换成一系列的向量
        System.out.println(embeddingStore);
    }

    /**
     * 文档分割
     */
    @Test
    public void testDocumentSplitter() {
        // 使用 ClassPathDocumentLoader 读取指定目录下的知识库文档
        // 并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = ClassPathDocumentLoader.loadDocument("knowledge/人工智能.md");
        // 为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        // 自定义文档分割器
        // 按段落分割文档：每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
        // 注意：当段落长度总和小于设定的最大长度时，就不会有重叠的必要。
        DocumentByParagraphSplitter documentSplitter1 = new DocumentByParagraphSplitter(
                300,
                30,
                // token分词器：按token计算
                new HuggingFaceTokenCountEstimator());
        // 按字符计算，每个片段包含不超过 300个字符，并且有 30个字符的重叠部分保证连贯性
        DocumentByParagraphSplitter documentSplitter2 = new DocumentByParagraphSplitter(300, 30);
        EmbeddingStoreIngestor
                .builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentSplitter1)
                .build()
                .ingest(document);

        List<TextSegment> textSegmentList1 = documentSplitter1.split(document);
        List<TextSegment> textSegmentList2 = documentSplitter2.split(document);

        System.out.println(textSegmentList1);
        System.out.println("=================");
        System.out.println(textSegmentList2);
    }

    /**
     * 测试 HuggingFaceTokenizer 分词
     */
    @Test
    public void testTokenize() {
        HuggingFaceTokenCountEstimator huggingFaceTokenizer = new HuggingFaceTokenCountEstimator();
        // 要 debug 进去，才能看到分词结果，但是里面的结果我看不懂，有些文字会转换为模型可以理解的特殊标记
        int i = huggingFaceTokenizer.estimateTokenCountInText("我叫小张，我今年18岁，我住在上海");
        System.out.println(i);
    }

    @Test
    public void testTokenCount() {
        String text = "这是一个示例文本，用于测试 token 长度的计算。";
        UserMessage userMessage = UserMessage.userMessage(text);
        // 计算 token 长度
//         QwenTokenCountEstimator tokenizer = new QwenTokenCountEstimator(System.getenv("DASH_SCOPE_API_KEY"), "qwen-max");
        HuggingFaceTokenCountEstimator tokenizer = new HuggingFaceTokenCountEstimator();
        int count = tokenizer.estimateTokenCountInMessage(userMessage);
        System.out.println("token长度：" + count);
    }


}
