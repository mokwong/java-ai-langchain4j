package cn.mo.java.ai.langchain4j.mysql.service.impl;

import cn.mo.java.ai.langchain4j.mysql.mapper.MessageMapper;
import cn.mo.java.ai.langchain4j.mysql.model.Message;
import cn.mo.java.ai.langchain4j.mysql.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author mo
 * @Description 与大模型的聊天记录持久化
 * @createTime 2025年07月02日 16:14
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public int upsert(Message record) {
        return baseMapper.upsert(record);
    }

}
