package cn.tmkit.core.convert;

import cn.tmkit.core.lang.Booleans;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

/**
 * 字符转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public class CharacterConverter extends AbstractConverter<Character> {

    private static final long serialVersionUID = 2023L;

    @Override
    protected Character handleInternal(@NotNull Object value) {
        if (value instanceof Boolean) {
            return Booleans.toCharacter((Boolean) value);
        }
        final String valueStr = execToStr(value);
        if (Strings.hasText(valueStr)) {
            return valueStr.charAt(0);
        }
        return null;
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Character> getTargetClass() {
        return Character.class;
    }

    /**
     * 返回{@linkplain  CharacterConverter}实例对象
     *
     * @return {@linkplain  CharacterConverter}
     */
    public static CharacterConverter getInstance() {
        return Singletons.get(CharacterConverter.class);
    }

}
