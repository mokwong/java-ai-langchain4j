package cn.mo.java.ai.langchain4j.mysql.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mo
 * @Description test
 * @createTime 2025年07月15日 14:30
 */
@SpringBootTest
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Test
    void test() {
        System.out.println(messageService.list());
    }
}