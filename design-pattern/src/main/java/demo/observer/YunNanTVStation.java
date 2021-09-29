package demo.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-09-29
 */
public class YunNanTVStation implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof WeatherStation) {
            WeatherStation weatherStation = (WeatherStation) o;
            System.out.println("==========云南电视台" + arg + "=========");
            System.out.println("明日气温:" + weatherStation.getTemperature());
            System.out.println("明日气压:" + weatherStation.getPressure());
            System.out.println("明日空气湿度:" + weatherStation.getHumidity());
        }
    }
}
