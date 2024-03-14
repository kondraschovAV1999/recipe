package learning.spring.recipe.mappers;

import java.time.LocalTime;

public class TimeMapper {

    public Integer asLocalTime(LocalTime time) {
        return time != null ? time.toSecondOfDay() / 60 : 0;
    }

    public LocalTime asInteger(Integer time) {
        return time != null ? LocalTime.ofSecondOfDay(time * 60) :
                LocalTime.ofSecondOfDay(0);
    }
}
