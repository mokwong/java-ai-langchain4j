package cn.mo.java.ai.langchain4j.mysql;

import cn.mo.java.ai.langchain4j.mysql.model.Message;
import cn.mo.java.ai.langchain4j.mysql.service.MessageService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author mo
 * @Description mysql 存储聊天记录
 * @createTime 2025年07月02日 16:26
 */
@Component
public class MySqlChatMemoryStore implements ChatMemoryStore {

    public static final String LIMIT_ONE = "limit 1";

    @Autowired
    private MessageService messageService;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        check(memoryId);
        Message message = messageService
                .lambdaQuery()
                .eq(Message::getSessionId, memoryId)
                .last(LIMIT_ONE)
                .one();
        if (message == null) {
            return Collections.emptyList();
        }
        // 使用 langchain4j 的 json 反序列化
        return ChatMessageDeserializer.messagesFromJson(message.getMessageList());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        check(memoryId);
        Message message = new Message();
        message.setSessionId(String.valueOf(memoryId));
        // 使用 langchain4j 的 json 序列化
        message.setMessageList(ChatMessageSerializer.messagesToJson(messages));
        Date date = new Date();
        message.setCreateTime(date);
        message.setUpdateTime(date);
        messageService.upsert(message);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        check(memoryId);
        messageService.remove(Wrappers.<Message>lambdaQuery().eq(Message::getSessionId, memoryId));
    }

    private void check(Object memoryId) {
        Assert.isTrue(memoryId instanceof String, "memoryId must be String");
    }

}
