package cn.mo.java.ai.langchain4j.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mo
 * @Description 聊天记忆消息窗口自动装配
 * @createTime 2025年07月02日 14:10
 */
@Configuration
public class MemoryChatAssistantConfig {

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }

}
