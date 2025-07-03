package cn.mo.java.ai.langchain4j.mysql.mapper;

import cn.mo.java.ai.langchain4j.mysql.model.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mo
 * @Description 挂号预约记录表
 * @createTime 2025年07月03日 17:46
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {

}