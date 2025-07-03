package cn.mo.java.ai.langchain4j.session;

/**
 * @author mo
 * @Description web session id holder
 * @createTime 2025年07月03日 15:05
 */
public class WebSessionIdHolder {

    private static final ThreadLocal<String> SESSION_ID = new ThreadLocal<>();

    public static void setSessionId(String sessionId) {
        SESSION_ID.set(sessionId);
    }

    public static String getSessionId() {
        return SESSION_ID.get();
    }

    public static void removeSessionId() {
        SESSION_ID.remove();
    }

}
