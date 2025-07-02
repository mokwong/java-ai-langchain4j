package cn.mo.java.ai.langchain4j.mysql.mapper;

import cn.mo.java.ai.langchain4j.mysql.model.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mo
 * @Description 与大模型的聊天记录持久化
 * @createTime 2025年07月02日 16:14
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    int upsert(Message record);

}