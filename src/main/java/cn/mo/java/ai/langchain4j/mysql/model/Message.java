package cn.mo.java.ai.langchain4j.mysql.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 与大模型的聊天记录
 */
@Data
public class Message {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 聊天会话id
     */
    private String sessionId;

    /**
     * 聊天记录集合
     */
    private String messageList;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近修改时间
     */
    private Date updateTime;
}