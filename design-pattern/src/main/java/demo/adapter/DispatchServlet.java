package demo.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jingxin
 * @date 2022-05-15
 */
public class DispatchServlet {

    private List<HandlerAdapter> handlerAdapters;

    {
        handlerAdapters = new ArrayList<>();
        handlerAdapters.add(new HttpHandlerAdapter());
        handlerAdapters.add(new SimpleHandlerAdapter());
        handlerAdapters.add(new AnnotationHandlerAdapter());
    }

    public void doDispatch(Controller handler) {
        getHandlerAdapter(handler).handle(handler);
    }

    public HandlerAdapter getHandlerAdapter(Controller controller) {
        return handlerAdapters.stream()
                .filter(h -> h.supports(controller))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        DispatchServlet dispatchServlet = new DispatchServlet();
        dispatchServlet.doDispatch(new HttpController());
        dispatchServlet.doDispatch(new SimpleController());
        dispatchServlet.doDispatch(new AnnotationController());
    }
}
