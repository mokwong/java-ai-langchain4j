package cn.mo.java.ai.langchain4j.controller;

import cn.mo.java.ai.langchain4j.assistant.XiaoZhiAgent;
import cn.mo.java.ai.langchain4j.dto.req.ChatForm;
import cn.mo.java.ai.langchain4j.session.WebSessionIdHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mo
 * @Description controller
 * @createTime 2025年07月03日 14:42
 */
@Slf4j
@Tag(name = "小智医疗客服")
@RestController
@RequestMapping("/xiaoZhi")
public class XiaoZhiController {

    @Autowired
    private XiaoZhiAgent xiaoZhiAgent;

    @Operation(summary = "对话")
    @PostMapping("/chat")
    public String chat(@RequestBody ChatForm chatForm) {
        String sessionId = WebSessionIdHolder.getSessionId();
        log.debug("sessionId: {}", sessionId);
        return xiaoZhiAgent.chat(sessionId, chatForm.getUserMessage());
    }

}



