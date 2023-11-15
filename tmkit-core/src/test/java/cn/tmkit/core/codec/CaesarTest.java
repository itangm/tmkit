package cn.tmkit.core.codec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-10-26
 */
public class CaesarTest {

    @Test
    public void encode() {
        String str = Base64s.encode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnRJZCI6MTQ2OTg3NjcyNzE5OTE2NjQ2NSwidXNlcklkIjoxNDY5ODc2NzI3MTk5MTY2NDY1LCJuaWNrbmFtZSI6ImdvdWdlIiwidXNlcm5hbWUiOiJ6aGFuZ3NhbiIsImRldmljZVR5cGUiOiJXWF9NSU5JIiwiaXNzIjoibW9yZWQifQ.htKIsy5DHjROG8_VHJr38WNKCkkjwYnwKZGM2ZADjQA");
        System.out.println("原文 = " + str);
        System.out.println();
        String encode = Caesar.encode(str, 15);
        System.out.println("凯撒密文 = " + encode);
        System.out.println();
       String text = Caesar.decode(encode, 15);
        System.out.println("解密后 = " + text);
        assertEquals(str, text);
    }

}
