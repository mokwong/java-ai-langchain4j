package cn.mo.java.ai.langchain4j.config;

import cn.mo.java.ai.langchain4j.mysql.MySqlChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mo
 * @Description 小智客服 mysql持久化聊天记录
 * @createTime 2025年07月02日 16:49
 */
@Configuration
public class XiaoZhiChatAssistantConfig {

    @Autowired
    private XiaoZhiConfig xiaoZhiConfig;

    /**
     * 这个设计还是很优雅的，ChatMemoryProvider 底层是 MessageWindowChatMemory
     * MessageWindowChatMemory 底层才是 ChatMemoryStore
     * ChatMemoryStore 才是决定如何存储的关键
     *
     * @param mySqlChatMemoryStore
     * @return
     */
    @Bean
    public ChatMemoryProvider xiaoZhiChatMemoryProvider(MySqlChatMemoryStore mySqlChatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(xiaoZhiConfig.getMemorySize().intValue())
                .chatMemoryStore(mySqlChatMemoryStore) // 底层存储到 mysql
                .build();
    }

}
