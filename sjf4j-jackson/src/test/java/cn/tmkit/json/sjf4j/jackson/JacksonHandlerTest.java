package cn.tmkit.json.sjf4j.jackson;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain JacksonHandler}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class JacksonHandlerTest {

    @Test
    public void doSerialize() {
        LocalDateTime now = LocalDateTime.now();
        JacksonHandler jacksonHandler = new JacksonHandler();
        System.out.println(jacksonHandler.serialize(now));

    }

    @Test
    public void testDoSerialize() {
    }

    @Test
    public void doDeserialize() {
    }

}
