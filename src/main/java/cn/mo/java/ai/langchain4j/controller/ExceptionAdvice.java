package cn.mo.java.ai.langchain4j.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author mo
 * @Description 异常处理
 * @createTime 2025年07月03日 15:10
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error("Exception:", e);
        return "error";
    }

}
