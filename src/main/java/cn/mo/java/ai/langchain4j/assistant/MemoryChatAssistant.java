package cn.mo.java.ai.langchain4j.assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author mo
 * @Description 带记忆功能，所有聊天共用一个聊天记录消息窗口，多用户使用会混乱
 * @createTime 2025年07月02日 14:09
 */
@AiService(wiringMode = EXPLICIT, chatModel = "openAiChatModel", chatMemory = "chatMemory")
public interface MemoryChatAssistant {

    String chat(String userMessage);

}
