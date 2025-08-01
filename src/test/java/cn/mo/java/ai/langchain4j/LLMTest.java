package cn.mo.java.ai.langchain4j;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mo
 * @Description test
 * @createTime 2025年07月01日 15:09
 */
@SpringBootTest(classes = Application.class)
public class LLMTest {


    /**
     * 整合SpringBoot
     */
    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
    public void testSpringBoot() {
        //向模型提问
        String answer = openAiChatModel.chat("你好");
        //输出结果
        System.out.println(answer);
    }

    @Autowired
    private OllamaChatModel ollamaChatModel;

    @Test
    public void testOllama() {
        //向模型提问
        String answer = ollamaChatModel.chat("你好");
        //输出结果
        System.out.println(answer);
    }

    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    public void testDashScopeQwen() {
        //向模型提问
        String answer = qwenChatModel.chat("你好");
        //输出结果
        System.out.println(answer);
    }

    /**
     * 通义万象生成图片
     * 图片模型没有自动注入的 bean，所以自己创建模型实例
     */
    @Test
    public void testDashScopeWanx() {
        WanxImageModel wanxImageModel = WanxImageModel.builder()
                .modelName("wanx2.1-t2i-plus")
                .apiKey(System.getenv("DASH_SCOPE_API_KEY"))
                .build();
        Response<Image> response = wanxImageModel.generate("奇幻森林精灵：在一片弥漫着轻柔薄雾的古老森林深处，阳光透过茂密枝叶洒下金色光斑。一位身材娇小、长着透明薄翼的精灵少女站在一朵硕大的蘑菇上。她有着海藻般的绿色长发，发间点缀着蓝色的小花，皮肤泛着珍珠般的微光。身上穿着由翠绿树叶和白色藤蔓编织而成的连衣裙，手中捧着一颗散发着柔和光芒的水晶球，周围环绕着五彩斑斓的蝴蝶，脚下是铺满苔藓的地面，蘑菇和蕨类植物丛生，营造出神秘而梦幻的氛围。");
        System.out.println(response.content().url());
    }


    public static void main(String[] args) {
        // 使用 LangChain4j 提供的测试 apiKey，免费的，有使用配额限制，且仅限于 gpt-4o-mini 模型。
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1") //设置模型api地址
                .apiKey("demo") //设置模型apiKey
                .modelName("gpt-4o-mini") //设置模型名称
                .build();
        //向模型提问
        String answer = model.chat("你好");
        //输出结果
        System.out.println(answer);
    }

}
