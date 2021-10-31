package demo.mediator;

import java.util.HashMap;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class ConcreteMediator extends Mediator {

    //所有家电
    private HashMap<String, HomeAppliance> colleagueHMap;
    //家电名字
    private HashMap<String, String> interMap;

    public ConcreteMediator() {
        colleagueHMap = new HashMap<>();
        interMap = new HashMap<>();
    }

    @Override
    public void register(String name, HomeAppliance device) {
        colleagueHMap.put(name, device);
        if (device instanceof Alarm) {
            interMap.put("alarm", name);
        } else if (device instanceof TV) {
            interMap.put("tv", name);
        } else if (device instanceof Curtain) {
            interMap.put("curtain", name);
        } else if (device instanceof CoffeeMachine) {
            interMap.put("coffeeMachine", name);
        }
    }

    /**
     * 具体中介者的核心方法
     * 1.根据得到的消息完成对应的任务
     * 2.中介者在这个方法协调各家电完成任务
     */
    @Override
    public void getMessage(int stateChange, String applianceName) {
        HomeAppliance homeAppliance = colleagueHMap.get(applianceName);
        if (homeAppliance instanceof Alarm) {
            if (stateChange == 0) {
                ((Curtain)(colleagueHMap.get(interMap.get("curtain")))).up();
                ((CoffeeMachine)(colleagueHMap.get(interMap.get("coffeeMachine")))).makeCoffee();
                ((TV)(colleagueHMap.get(interMap.get("tv")))).open();
            } else if (stateChange == 1) {
                ((TV)(colleagueHMap.get(interMap.get("tv")))).stop();
                ((Curtain)(colleagueHMap.get(interMap.get("curtain")))).down();
            }
        } else if (homeAppliance instanceof CoffeeMachine) {
            ((Curtain)(colleagueHMap.get(interMap.get("curtain")))).up();
        }
    }

    @Override
    public void sendMessage() {

    }
}
