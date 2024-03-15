package learning.spring.recipe.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeMapperTest {

    private TimeMapper timeMapper;

    @BeforeEach
    void setUp() {
        timeMapper = new TimeMapper();
    }

    @Test
    void asLocalTime() {
        LocalTime time = LocalTime.of(1, 0);
        Integer timeInt = timeMapper.asLocalTime(time);

        assertEquals(60, timeInt);

    }

    @Test
    void asInteger() {
        LocalTime time = timeMapper.asInteger(30);

        assertEquals(30, time.getMinute());
    }
}