package cn.mo.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author mo
 * @Description 小智医疗客服
 * @createTime 2025年07月03日 14:26
 */
@AiService(
        wiringMode = EXPLICIT,
        chatModel = "openAiChatModel",
        chatMemoryProvider = "xiaoZhiChatMemoryProvider",
        tools = {"appointmentTools"},
        contentRetriever = "contentRetrieverXiaozhi"
)
public interface XiaoZhiAgent {

    @SystemMessage(fromResource = "xiao-zhi-system-prompt-template.txt")
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);

}
