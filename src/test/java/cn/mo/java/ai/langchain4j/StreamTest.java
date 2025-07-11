package cn.mo.java.ai.langchain4j;

import cn.mo.java.ai.langchain4j.assistant.XiaoZhiAgent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootTest
public class StreamTest {

    @Autowired
    private XiaoZhiAgent xiaoZhiAgent;

    @Test
    public void test() throws Exception {
        Flux<String> chat = xiaoZhiAgent.chat("sessionId", "你好");
        chat.subscribe(System.out::println);
        Thread.sleep(10000);
    }

}
