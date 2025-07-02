package cn.mo.java.ai.langchain4j.mysql.service;

import cn.mo.java.ai.langchain4j.mysql.model.Message;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author mo
 * @Description 与大模型的聊天记录持久化
 * @createTime 2025年07月02日 16:14
 */
public interface MessageService extends IService<Message> {

    int upsert(Message record);

}
