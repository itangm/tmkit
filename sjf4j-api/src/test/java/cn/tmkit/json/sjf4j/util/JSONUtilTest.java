package cn.tmkit.json.sjf4j.util;

import cn.tmkit.core.support.Console;
import cn.tmkit.json.sjf4j.BaseTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Tester for {@linkplain JSONUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-13
 */
public class JSONUtilTest {

    @Test
    public void typeRef() {
        class ListLongType extends BaseTypeRef<List<Long>> {

        }
        ListLongType listLongType = new ListLongType();
        Type type = listLongType.getType();
        Console.log(type);
        Console.log(type.getClass());
        Console.log(type.getTypeName());
        Class<? super List<Long>> rawType = listLongType.getRawType();
        Console.log(rawType);
    }

    @Test
    public void toJson() {

    }

    @Test
    void testToJson() {
    }

    @Test
    void testToJson1() {
    }

    @Test
    void fromJson() {
    }

    @Test
    void testFromJson() {
    }

    @Test
    void testFromJson1() {
    }
}
