package cn.mo.java.ai.langchain4j.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author mo
 * @Description 小智客服 动态配置
 * @createTime 2025年07月03日 14:36
 */
@Data
@Component
@ConfigurationProperties(prefix = "xiao-zhi")
public class XiaoZhiConfig {

    private Long memorySize = 20L;

}
