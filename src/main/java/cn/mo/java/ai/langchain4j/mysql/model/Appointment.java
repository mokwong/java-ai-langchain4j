package cn.mo.java.ai.langchain4j.mysql.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author mo
 * @Description 挂号预约记录表
 * @createTime 2025年07月03日 17:46
 */
/*
CREATE TABLE `appointment` (
	`id` BIGINT(19) NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(50) NOT NULL COMMENT '用户名' COLLATE 'utf8mb4_general_ci',
	`id_card` VARCHAR(18) NOT NULL COMMENT '身份证' COLLATE 'utf8mb4_general_ci',
	`department` VARCHAR(50) NOT NULL COMMENT '预约科室' COLLATE 'utf8mb4_general_ci',
	`date` VARCHAR(10) NOT NULL COMMENT '日期' COLLATE 'utf8mb4_general_ci',
	`time` VARCHAR(10) NOT NULL COMMENT '时间，上午/下午' COLLATE 'utf8mb4_general_ci',
	`doctor_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '医生名称' COLLATE 'utf8mb4_general_ci',
	`create_time` DATETIME NULL DEFAULT (now()),
	`update_time` DATETIME NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;
 */
@Data
public class Appointment {

    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 预约科室
     */
    private String department;

    /**
     * 日期
     */
    private String date;

    /**
     * 时间，上午/下午
     */
    private String time;

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 创建时间
     */
    @JsonIgnore // Date 类型，大模型的回答无法反序列出 Date 对象，所以这里忽略
    private Date createTime;

    /**
     * 最近修改时间
     */
    @JsonIgnore
    private Date updateTime;
}