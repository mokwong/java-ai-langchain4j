package cn.mo.java.ai.langchain4j;

import cn.mo.java.ai.langchain4j.assistant.Assistant;
import cn.mo.java.ai.langchain4j.assistant.MemoryChatAssistant;
import cn.mo.java.ai.langchain4j.assistant.SeparateChatAssistant;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author mo
 * @Description 聊天记忆测试
 * @createTime 2025年07月02日 13:49
 */
@SpringBootTest
public class ChatMemoryTest {

    @Autowired
    private Assistant assistant;

    /**
     * 没有记忆的对话
     */
    @Test
    public void testChatMemory() {
        String answer1 = assistant.chat("你好，我是马化腾");
        System.out.println(answer1);

        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);
    }

    @Autowired
    private OpenAiChatModel openAiChatModel;

    /**
     * 简单实现的聊天记忆
     */
    @Test
    public void testChatMemory2() {
        UserMessage userMessage1 = UserMessage.userMessage("你好，我是马化腾");
        ChatResponse chatResponse1 = openAiChatModel.chat(userMessage1);
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        System.out.println(aiMessage1.text());

        UserMessage userMessage2 = UserMessage.userMessage("我是谁");
        /*
        - method: POST
        - url: http://langchain4j.dev/demo/openai/v1/chat/completions
        - headers: [Authorization: Beare...mo], [User-Agent: langchain4j-openai], [Content-Type: application/json]
        - body: {
          "model" : "gpt-4o-mini",
          "messages" : [ {
            "role" : "user",
            "content" : "你好，我是马化腾"
          }, {
            "role" : "assistant",
            "content" : "你好，马化腾！很高兴能和你聊天。有什么我可以帮助你的吗？"
          }, {
            "role" : "user",
            "content" : "我是谁"
          } ],
          "temperature" : 1.0,
          "stream" : false
        }
         */
        ChatResponse chatResponse2 = openAiChatModel.chat(Arrays.asList(userMessage1, aiMessage1, userMessage2));
        AiMessage aiMessage2 = chatResponse2.aiMessage();
        System.out.println(aiMessage2.text());
    }

    /**
     * 使用ChatMemory的聊天记忆
     */
    @Test
    public void testChatMemory3() {
        // 聊天记忆消息窗口，最多10条消息
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        // 指定聊天记忆消息窗口
        Assistant assistant = AiServices
                .builder(Assistant.class)
                .chatModel(openAiChatModel)
                .chatMemory(chatMemory)
                .build();
        String answer1 = assistant.chat("你好，我是马化腾");
        System.out.println(answer1);
        /*
        - method: POST
        - url: http://langchain4j.dev/demo/openai/v1/chat/completions
        - headers: [Authorization: Beare...mo], [User-Agent: langchain4j-openai], [Content-Type: application/json]
        - body: {
          "model" : "gpt-4o-mini",
          "messages" : [ {
            "role" : "user",
            "content" : "你好，我是马化腾"
          }, {
            "role" : "assistant",
            "content" : "你好，马化腾！很高兴和你交流。请问有什么我可以帮助你的吗？"
          }, {
            "role" : "user",
            "content" : "我是谁"
          } ],
          "temperature" : 1.0,
          "stream" : false
        }
         */
        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);
    }

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    @Test
    public void testChatMemory4() {
        String answer1 = memoryChatAssistant.chat("你好，我是马化腾");
        System.out.println(answer1);
        String answer2 = memoryChatAssistant.chat("我是谁");
        System.out.println(answer2);
    }


    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testChatMemory5() {
        String answer1 = separateChatAssistant.chat("1", "你好，我是马化腾");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat("1", "我是谁");
        System.out.println(answer2);

        // 2 号没有聊天记录，所以不知道我是谁
        String answer3 = separateChatAssistant.chat("2", "我是谁");
        System.out.println(answer3);
    }

}
