package cn.tmkit.json.sjf4j.jackson;

import cn.tmkit.json.sjf4j.JsonHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tester for {@linkplain JacksonHandler}
 *
 * @author miles.tang
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

    @Test
    public void deserializeList() {
        String jsonArray = "[{\"id\":1,\"name\":\"zs\"},{\"id\":2,\"name\":\"ls\"}]";
        JsonHandler jsonHandler = new JacksonHandler();
        List<Person> list = jsonHandler.deserializeList(jsonArray, Person.class);
        assertNotNull(list);
        assertEquals(2, list.size());
        System.out.println("list = " + list);
    }

    @Test
    public void deserializeList2() {
        String jsonArray = "[{\"id\":1,\"name\":\"zs\"},{\"id\":2,\"name\":\"ls\"}]";
        JsonHandler jsonHandler = new JacksonHandler();
        long start = System.currentTimeMillis();
        List<Person> list = jsonHandler.deserializeList(jsonArray, Person.class);
        long end1 = System.currentTimeMillis();
        System.out.println("cost = " + (end1 - start));
        list = jsonHandler.deserializeList(jsonArray, Person.class);
        long end2 = System.currentTimeMillis();
        System.out.println("cost2 = " + (end2 - end1));
        list = jsonHandler.deserializeList(jsonArray, Person.class);
        long end3 = System.currentTimeMillis();
        System.out.println("cost3 = " + (end3 - end2));
    }

    @Test
    public void deserializeList3() {
        String jsonArray = "[{\"id\":1,\"name\":\"zs\"},{\"id\":2,\"name\":\"ls\"}]";
        JsonHandler jsonHandler = new JacksonHandler();
        long start = System.currentTimeMillis();
        List<Person> list = jsonHandler.deserialize(jsonArray, new TypeReference<List<Person>>() {
        }.getType());
        long end1 = System.currentTimeMillis();
        System.out.println("cost = " + (end1 - start));

        list = jsonHandler.deserialize(jsonArray, new TypeReference<List<Person>>() {
        }.getType());
        long end2 = System.currentTimeMillis();
        System.out.println("cost2 = " + (end2 - end1));
        list = jsonHandler.deserialize(jsonArray, new TypeReference<List<Person>>() {
        }.getType());
        long end3 = System.currentTimeMillis();
        System.out.println("cost3 = " + (end3 - end2));
    }

    public static class Person {

        private Long id;

        private String name;

        public Person() {
        }

        public Person(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
