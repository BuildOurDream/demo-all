package demo.gateway.config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-09-21
 */
public class CustomApplicationListener implements SmartApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("ApplicationListener........ " + event.toString());
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
//        return DemoEvent.class.isAssignableFrom(eventType);
        return true;
    }
}
