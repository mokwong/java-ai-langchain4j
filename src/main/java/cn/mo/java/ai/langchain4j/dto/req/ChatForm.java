package cn.mo.java.ai.langchain4j.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author mo
 * @Description 对话
 * @createTime 2025年07月03日 14:41
 */
@Data
public class ChatForm {

    @NotBlank(message = "请输入用户提示词")
    private String userMessage;

}
