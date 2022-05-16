package demo.adapter;

/**
 * @author jingxin
 * @date 2022-05-15
 */
public interface HandlerAdapter {

    boolean supports(Object handler);

    void handle(Object handler);
}

class HttpHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HttpController;
    }

    @Override
    public void handle(Object handler) {
        HttpController httpController = (HttpController) handler;
        httpController.doHttpHandler();
    }
}

class SimpleHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof SimpleController;
    }

    @Override
    public void handle(Object handler) {
        SimpleController simpleController = (SimpleController) handler;
        simpleController.doSimpleHandler();
    }
}

class AnnotationHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof AnnotationController;
    }

    @Override
    public void handle(Object handler) {
        AnnotationController annotationController = (AnnotationController) handler;
        annotationController.doAnnotationHandler();
    }
}