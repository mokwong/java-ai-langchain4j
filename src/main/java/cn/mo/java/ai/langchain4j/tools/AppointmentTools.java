package cn.mo.java.ai.langchain4j.tools;

import cn.mo.java.ai.langchain4j.mysql.model.Appointment;
import cn.mo.java.ai.langchain4j.mysql.service.AppointmentService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mo
 * @Description 挂号预约记录表封装成 tool
 * @createTime 2025年07月03日 17:51
 */
@Slf4j
@Component
public class AppointmentTools {

    @Autowired
    private AppointmentService appointmentService;


    @Tool(name = "查询是否有挂号号源", value = "根据科室名称、日期、时间查询是否有号源，并返回给用户")
    public boolean checkAreThereAnyNumberSource(
            @P(value = "科室名称") String department,
            @P(value = "日期") String date,
            @P(value = "时间，可选值：上午、下午") String time) {
        log.info("查询科室：{}，日期：{}，时间：{}", department, date, time);
        Long count = appointmentService.lambdaQuery()
                .eq(Appointment::getDepartment, department)
                .eq(Appointment::getDate, date)
                .eq(Appointment::getTime, time)
                .count();
        return count <= 0;
    }

    @Tool(name = "预约挂号", value = "根据参数，先执行工具方法queryDepartment查询是否可预约，并直接给用户回答是否可预约，并让用户确认所有预约信息，用户确认后再进行预约。")
    public String bookAppointment(Appointment appointment) {
        //查找数据库中是否包含对应的预约记录
        boolean anyNumberSource = checkAreThereAnyNumberSource(appointment.getDepartment(), appointment.getDate(), appointment.getTime());
        if (!anyNumberSource) {
            return "您选择的科室、日期、时间没有号源，请重新选择";
        } else {
            appointment.setId(null);//防止大模型幻觉设置了id
            boolean save = appointmentService.save(appointment);
            if (save) {
                return "预约成功，并返回预约详情";
            } else {
                return "预约失败";
            }
        }
    }

    @Tool(name = "取消预约挂号", value = "根据参数，查询预约是否存在，如果存在则删除预约记录并返回取消预约成功，否则返回取消预约失败")
    public String cancelAppointment(Appointment appointment) {
        boolean anyNumberSource = checkAreThereAnyNumberSource(appointment.getDepartment(), appointment.getDate(), appointment.getTime());
        if (!anyNumberSource) {
            return "您没有预约记录，请核对预约科室和时间";
        } else {
            boolean remove = appointmentService.remove(Wrappers.<Appointment>lambdaQuery()
                    .eq(Appointment::getDepartment, appointment.getDepartment())
                    .eq(Appointment::getDate, appointment.getDate())
                    .eq(Appointment::getTime, appointment.getTime()));
            if (remove) {
                return "取消预约成功";
            } else {
                return "取消预约失败";
            }
        }
    }

}
