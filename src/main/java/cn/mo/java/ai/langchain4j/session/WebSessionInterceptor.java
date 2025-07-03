package cn.mo.java.ai.langchain4j.session;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author mo
 * @Description tomcat session 拦截器
 * @createTime 2025年07月03日 14:55
 */
public class WebSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        String sessionId = session.getId();
        WebSessionIdHolder.setSessionId(sessionId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WebSessionIdHolder.removeSessionId();
    }

}
