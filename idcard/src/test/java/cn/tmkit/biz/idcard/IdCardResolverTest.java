package cn.tmkit.biz.idcard;

import cn.tmkit.biz.idcard.constants.Gender;
import cn.tmkit.core.support.Console;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain IdCardResolver}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class IdCardResolverTest {

    @Test
    public void resolve() {
        IdCard idCard = IdCardResolver.resolve("130101201602018331");
        assertNotNull(idCard);
        String birthday = idCard.getBirthday();
        assertEquals("20160201", birthday);
        assertEquals(Gender.MALE, idCard.getGender());
        assertEquals("13", idCard.getProvince().getCode());
        Console.log(idCard.getProvince());
        LocalDate compared = LocalDate.of(2023, 1, 1);
        assertEquals(6, idCard.getAge(compared));
        compared = LocalDate.of(2023, 2, 24);
        assertEquals(7, idCard.getAge(compared));
        compared = LocalDate.of(2023, 2, 1);
        assertEquals(7, idCard.getAge(compared));

        LocalDate birthdayDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"));
        int age = (int) ChronoUnit.YEARS.between(birthdayDate, LocalDate.now());
        Console.log("Age is {}", age);
        assertEquals(age, idCard.getAge());

    }

}
