package cn.tmkit.json.sjf4j.gson;

import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Asserts;
import cn.tmkit.json.sjf4j.BaseJsonHandler;
import cn.tmkit.json.sjf4j.JsonRuntimeException;
import cn.tmkit.json.sjf4j.annotation.JsonProviderName;
import cn.tmkit.json.sjf4j.gson.deser.*;
import cn.tmkit.json.sjf4j.gson.ser.*;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.*;

/**
 * 基于{@code Gson}的JSON处理器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
@JsonProviderName(value = "gson", index = 40)
public class GsonJsonHandler extends BaseJsonHandler {

    private final Gson gson;

    public GsonJsonHandler() {
        this(createJson());
    }

    public GsonJsonHandler(Gson gson) {
        this.gson = Asserts.notNull(gson, "gson == null");
    }

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性名
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String doSerialize(@NotNull Object src, String[] ignorePropertyNames) throws JsonRuntimeException {
        if (Arrays.isNotEmpty(ignorePropertyNames)) {
            Gson customGson = this.gson.newBuilder()
                    .addSerializationExclusionStrategy(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes fa) {
                            return Arrays.contains(ignorePropertyNames, fa.getName());
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> aClass) {
                            return false;
                        }
                    })
                    .create();
            return customGson.toJson(src);
        } else {
            return serialize(src, src.getClass());
        }
    }

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String doSerialize(@NotNull Object src, Type typeOfT) throws JsonRuntimeException {
        return (typeOfT == null) ? gson.toJson(src) : gson.toJson(src, typeOfT);
    }

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    @Override
    public <T> T doDeserialize(@NotNull String json, @NotNull Type typeOfT) throws JsonRuntimeException {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * 创建支持JSR310的gson处理
     *
     * @return {@linkplain Gson}
     */
    public static Gson createJson() {
        GsonBuilder builder = new GsonBuilder();

        // 序列化
        builder.registerTypeAdapter(Instant.class, InstantSerializer.INSTANCE);

        builder.registerTypeAdapter(LocalDate.class, LocalDateSerializer.INSTANCE);
        builder.registerTypeAdapter(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        builder.registerTypeAdapter(LocalTime.class, LocalTimeSerializer.INSTANCE);

        builder.registerTypeAdapter(Year.class, YearSerializer.INSTANCE);
        builder.registerTypeAdapter(YearMonth.class, YearMonthSerializer.INSTANCE);
        builder.registerTypeAdapter(MonthDay.class, MonthDaySerializer.INSTANCE);

        builder.registerTypeAdapter(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        builder.registerTypeAdapter(OffsetTime.class, OffsetTimeSerializer.INSTANCE);

        builder.registerTypeAdapter(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);

        // 反序列化
        builder.registerTypeAdapter(LocalDate.class, LocalDateDeserializer.INSTANCE);
        builder.registerTypeAdapter(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        builder.registerTypeAdapter(LocalTime.class, LocalTimeDeserializer.INSTANCE);

        builder.registerTypeAdapter(Year.class, YearDeserializer.INSTANCE);
        builder.registerTypeAdapter(YearMonth.class, YearMonthDeserializer.INSTANCE);
        builder.registerTypeAdapter(MonthDay.class, MonthDayDeserializer.INSTANCE);

        builder.registerTypeAdapter(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        builder.registerTypeAdapter(OffsetDateTime.class, OffsetDateTimeDeserializer.class);

        builder.registerTypeAdapter(ZonedDateTime.class, ZonedDateTimeDeserializer.INSTANCE);

        return builder.create();
    }

}
