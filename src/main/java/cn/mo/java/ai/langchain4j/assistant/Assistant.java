package cn.mo.java.ai.langchain4j.assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author mo
 * @Description ai 助理接口，@AiService 注解可以实现自动创建代理对象，不需要自己去 AiServices.create();
 * @createTime 2025年07月02日 10:32
 */
@AiService(wiringMode = EXPLICIT, chatModel = "openAiChatModel")
public interface Assistant {

    String chat(String userMessage);

}
