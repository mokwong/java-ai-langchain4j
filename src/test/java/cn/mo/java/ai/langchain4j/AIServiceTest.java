package cn.mo.java.ai.langchain4j;

import cn.mo.java.ai.langchain4j.assistant.Assistant;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mo
 * @Description AI Service 测试类
 * @createTime 2025年07月02日 10:33
 */
@SpringBootTest
public class AIServiceTest {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
    public void testChat() {
        // 生成一个实现了 Assistant 接口的代理对象，该代理对象底层使用 OpenAiChatModel 与大模型进行交互
        Assistant assistant = AiServices.create(Assistant.class, openAiChatModel);
        String answer = assistant.chat("你好");
        System.out.println(answer);
    }

    /**
     * 使用 @AiService 注解可以实现自动创建代理对象
     */
    @Autowired
    private Assistant assistant;

    @Test
    public void testAssistant() {
        String answer = assistant.chat("你好");
        System.out.println(answer);
    }

}
