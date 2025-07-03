package cn.mo.java.ai.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mo
 * @Description function calling 自定义工具
 * @createTime 2025年07月03日 16:25
 */
@Slf4j
@Component
public class CalculatorTools {

    @Tool(name = "sum", value = "求和运算")
    public double sum(@P(value = "加数1", required = true) double a,
                      @P(value = "加数2", required = true) double b,
                      @ToolMemoryId String memoryId) {
        log.info("调用加法 sum:{},{},{}", a, b, memoryId);
        return a + b;
    }

    @Tool(name = "squareRoot", value = "平方根运算")
    public double squareRoot(double x, @ToolMemoryId String memoryId) {
        log.info("调用平方根运算:{}, {}", x, memoryId);
        return Math.sqrt(x);
    }

}
