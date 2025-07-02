package cn.mo.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author mo
 * @Description 隔离聊天记忆
 * @createTime 2025年07月02日 14:24
 */
@AiService(wiringMode = EXPLICIT, chatModel = "openAiChatModel", chatMemoryProvider = "chatMemoryProvider")
public interface SeparateChatAssistant {

    String chat(@MemoryId String memoryId, @UserMessage String userMessage);

}
