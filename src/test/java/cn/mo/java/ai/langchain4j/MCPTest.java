package cn.mo.java.ai.langchain4j;

import cn.mo.java.ai.langchain4j.assistant.Assistant;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.*;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.router.LanguageModelQueryRouter;
import dev.langchain4j.rag.query.router.QueryRouter;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mo
 * @Description mcp 客户端 test
 * @createTime 2025年07月15日 14:36
 */
@Slf4j
@SpringBootTest
public class MCPTest {

    @Autowired
    private ChatModel openAiChatModel;

    @Autowired
    private ContentRetriever contentRetrieverXiaozhiPincone;

    @Test
    public void test() {
        McpTransport transport = new HttpMcpTransport.Builder()
                .sseUrl("http://localhost:8081/sse")
                .logRequests(true)
                .logResponses(true)
                .build();
        McpClient mcpClient = new DefaultMcpClient.Builder()
                .key("webmvc-my-mcp-server-client")
                .transport(transport)
                .build();
        McpToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();
        List<ToolSpecification> toolSpecificationList = mcpClient.listTools();
        for (ToolSpecification toolSpecification : toolSpecificationList) {
            System.out.println(toolSpecification);
        }
        List<McpResource> mcpResources = mcpClient.listResources();
        for (McpResource mcpResource : mcpResources) {
            System.out.println(mcpResource);
            McpReadResourceResult resourceResult = mcpClient.readResource(mcpResource.uri());
//            System.out.println(resourceResult.toString());
            List<McpResourceContents> contents = resourceResult.contents();
            for (McpResourceContents content : contents) {
                McpResourceContents.Type type = content.type();
                if (type == McpResourceContents.Type.TEXT) {
                    McpTextResourceContents textResourceContents = (McpTextResourceContents) content;
                    try {
                        Files.write(Paths.get("d:/a16.txt"), textResourceContents.text().getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (type == McpResourceContents.Type.BLOB) {
                    McpBlobResourceContents blobResourceContents = (McpBlobResourceContents) content;
                    try {
                        Files.write(Paths.get("d:/a16.pdf"), Base64.getDecoder().decode(blobResourceContents.blob()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        Map<ContentRetriever, String> retrieverToDescription = new HashMap<>();
        retrieverToDescription.put(contentRetrieverXiaozhiPincone, "医院、课室信息");
        QueryRouter queryRouter = new LanguageModelQueryRouter(openAiChatModel, retrieverToDescription);
        RetrievalAugmentor retrievalAugmentor = DefaultRetrievalAugmentor.builder()
                .queryRouter(queryRouter)
                .build();
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(openAiChatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .toolProvider(toolProvider)
                .build();
        String chat = assistant.chat("今天广州的天气如何？");
        System.out.println(chat);
    }

}
