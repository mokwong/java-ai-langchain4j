package cn.mo.java.ai.langchain4j;

import cn.mo.java.ai.langchain4j.assistant.MemoryChatAssistant;
import cn.mo.java.ai.langchain4j.assistant.PromptMysqlSeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mo
 * @Description 提示词
 * @createTime 2025年07月03日 10:14
 */
@SpringBootTest
public class PromptTest {

    @Autowired
    private PromptMysqlSeparateChatAssistant promptMysqlSeparateChatAssistant;

    @Test
    public void testSystemMessage() {
        String answer = promptMysqlSeparateChatAssistant.chat("session-03", "今天几号");
        System.out.println(answer);
    }

    @Test
    public void testSystemMessageWithTemplate() {
        String answer = promptMysqlSeparateChatAssistant.chatWithSystemMessageTemplate("session-04", "今天几号");
        System.out.println(answer);
    }

    @Test
    public void testUserMessage() {
        String answer = promptMysqlSeparateChatAssistant.chatWithUserMessageTemplate("我是马化腾");
        System.out.println(answer);
    }

    @Test
    public void testUserInfo() {
        String answer = promptMysqlSeparateChatAssistant.chat3("session-05", "我是谁，我多大了", "翠花", 18);
        System.out.println(answer);
    }

    @Test
    public void testChat4() {
        String answer = promptMysqlSeparateChatAssistant.chat4("session-06", "你是谁");
        System.out.println(answer);
    }

}
