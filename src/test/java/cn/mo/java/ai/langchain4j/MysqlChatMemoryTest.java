package cn.mo.java.ai.langchain4j;

import cn.mo.java.ai.langchain4j.assistant.MysqlSeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mo
 * @Description mysql持久化聊天记录
 * @createTime 2025年07月02日 16:53
 */
@SpringBootTest
public class MysqlChatMemoryTest {

    @Autowired
    private MysqlSeparateChatAssistant mysqlSeparateChatAssistant;

    @Test
    public void testMysqlChatMemory() {

        String answer1 = mysqlSeparateChatAssistant.chat("session-01", "你好，我是马化腾呀");
        System.out.println(answer1);
        String answer2 = mysqlSeparateChatAssistant.chat("session-01", "我是谁呀");
        System.out.println(answer2);

        // 2 号没有聊天记录，所以不知道我是谁
        String answer3 = mysqlSeparateChatAssistant.chat("session-02", "我是谁呀");
        System.out.println(answer3);

    }

}
