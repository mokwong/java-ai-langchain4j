package cn.mo.java.ai.langchain4j.config;

import cn.mo.java.ai.langchain4j.mysql.MySqlChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mo
 * @Description mysql持久化
 * @createTime 2025年07月02日 16:49
 */
@Configuration
public class MysqlSeparateChatAssistantConfig {

    /**
     * 这个设计还是很优雅的，ChatMemoryProvider 底层是 MessageWindowChatMemory
     * MessageWindowChatMemory 底层才是 ChatMemoryStore
     * ChatMemoryStore 才是决定如何存储的关键
     *
     * @param mySqlChatMemoryStore
     * @return
     */
    @Bean
    public ChatMemoryProvider mysqlChatMemoryProvider(MySqlChatMemoryStore mySqlChatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(mySqlChatMemoryStore) // 底层存储到 mysql
                .build();
    }

}
