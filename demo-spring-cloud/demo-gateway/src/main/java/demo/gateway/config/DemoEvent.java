package demo.gateway.config;

import org.springframework.context.ApplicationEvent;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-09-21
 */
public class DemoEvent extends ApplicationEvent {

    private String msg;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public DemoEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }
}
