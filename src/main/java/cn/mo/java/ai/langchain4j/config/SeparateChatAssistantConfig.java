package cn.mo.java.ai.langchain4j.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mo
 * @Description 隔离聊天记忆消息窗口自动装配
 * @createTime 2025年07月02日 14:31
 */
@Configuration
public class SeparateChatAssistantConfig {

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .build();
    }

}
