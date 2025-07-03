package cn.mo.java.ai.langchain4j.mysql.service.impl;

import cn.mo.java.ai.langchain4j.mysql.mapper.AppointmentMapper;
import cn.mo.java.ai.langchain4j.mysql.model.Appointment;
import cn.mo.java.ai.langchain4j.mysql.service.AppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author mo
 * @Description 挂号预约记录表
 * @createTime 2025年07月03日 17:46
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {


}
