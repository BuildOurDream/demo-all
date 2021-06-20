package demo.mediator;

import java.util.HashMap;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class ConcreteMediator extends Mediator{

    private HashMap<String, Colleague> colleagueHMap;
    private HashMap<String, String> interMap;

    public ConcreteMediator() {
        colleagueHMap = new HashMap<>();
        interMap = new HashMap<>();
    }

    @Override
    public void register(String name, Colleague colleague) {
        colleagueHMap.put(name, colleague);
        if (colleague instanceof Alarm) {
            interMap.put("alarm", name);
        }
    }

    @Override
    public void getMessage(int stateChange, String colleagueName) {

    }

    @Override
    public void sendMessage() {

    }
}
