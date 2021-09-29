package demo.observer;

import lombok.Data;

import java.util.Observable;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-09-29
 */
@Data
public class WeatherStation extends Observable {

    private float temperature;

    private float pressure;

    private float humidity;

    public void publish(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        setChanged();
        notifyObservers("天气预报发布");
    }

    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();
        SicuanTVStation sicuanTVStation = new SicuanTVStation();
        weatherStation.addObserver(sicuanTVStation);
        YunNanTVStation yunNanTVStation = new YunNanTVStation();
        weatherStation.addObserver(yunNanTVStation);
        weatherStation.publish(24f, 60f, 66.6f);
    }
}
