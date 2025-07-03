package cn.mo.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author mo
 * @Description 提示词
 * @createTime 2025年07月03日 10:11
 */
@AiService(wiringMode = EXPLICIT, chatModel = "openAiChatModel", chatMemoryProvider = "mysqlChatMemoryProvider")
public interface PromptMysqlSeparateChatAssistant {

    @SystemMessage("你是我的好朋友，请使用粤语回答问题。今天是{{current_date}}")
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);

    @SystemMessage(fromResource = "my-prompt-template.txt")
    String chatWithSystemMessageTemplate(@MemoryId String memoryId, @UserMessage String userMessage);

    @UserMessage("你是我的好朋友，请用上海话回答问题，并且添加一些表情符号。{{it}}")
    String chatWithUserMessageTemplate(String message);

    @UserMessage("你是我的好朋友，请用上海话回答问题，并且添加一些表情符号。{{message}}")
    String chat(@V("message") String userMessage);

    @UserMessage("你是我的好朋友，请用粤语回答问题。{{message}}")
    String chat2(@MemoryId String memoryId, @V("message") String userMessage);

    @SystemMessage(fromResource = "my-prompt-template3.txt")
    String chat3(
            @MemoryId String memoryId,
            @UserMessage String userMessage,
            @V("username") String username,
            @V("age") int age
    );

    @UserMessage("你是我的好朋友，请用粤语回答问题。{{it}}")
    String chat4(@MemoryId String memoryId, @V("it") String userMessage);

}
