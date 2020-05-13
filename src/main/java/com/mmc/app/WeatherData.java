package com.mmc.app;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherData implements Comparable {
    private long time;
    private float temp;
    private float pressure;

    @Override
    public int compareTo(Object o) {

        return Long.compare(time, ((WeatherData) o).time);
    }

    public String toString() {
        return time + ";" + temp + ";" + pressure;
    }
}
