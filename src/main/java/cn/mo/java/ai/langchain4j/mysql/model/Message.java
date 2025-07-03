package cn.mo.java.ai.langchain4j.mysql.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 与大模型的聊天记录
 */
/*
CREATE TABLE `message` (
	`id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '自增id',
	`session_id` VARCHAR(50) NOT NULL COMMENT '聊天会话id' COLLATE 'utf8mb4_general_ci',
	`message_list` JSON NOT NULL COMMENT '聊天记录集合',
	`create_time` DATETIME NOT NULL DEFAULT (now()) COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `uq_session_id` (`session_id`) USING BTREE
)
COMMENT='与大模型的聊天记录'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=13
;
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