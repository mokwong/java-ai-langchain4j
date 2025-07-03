package cn.mo.java.ai.langchain4j;

import cn.mo.java.ai.langchain4j.assistant.MysqlSeparateChatAssistant;
import cn.mo.java.ai.langchain4j.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mo
 * @Description 工具测试
 * @createTime 2025年07月03日 16:20
 */
@SpringBootTest
public class ToolTest {

    @Autowired
    private MysqlSeparateChatAssistant mysqlSeparateChatAssistant;

    /**
     * 大模型不擅长数学计算，提供工具后会选择工具
     */
    @Test
    public void testCalculatorTools() {
        String answer = mysqlSeparateChatAssistant.chat("session-08", "1+2等于几，475695037565的平方根是多少？");
        //答案：3，689706.4865
        /*
        1 + 2 等于 3。
        475695037565 的平方根约为 689675.202。当使用计算器或数学软件进行计算时，可以得到更精确的结果。
         */
        System.out.println(answer);
    }

}
