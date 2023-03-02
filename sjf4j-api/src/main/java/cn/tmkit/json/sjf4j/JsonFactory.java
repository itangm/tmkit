package cn.tmkit.json.sjf4j;

import cn.tmkit.core.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import cn.tmkit.json.sjf4j.annotation.JsonProviderName;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JSON Factory
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
@Slf4j
public class JsonFactory {

    private static JsonHandler defaultJsonHandler;

    private static final Map<String, JsonHandler> ALL = new ConcurrentHashMap<>(4);

    static {
        findJsonHandlerImplClasses();
    }

    private static void findJsonHandlerImplClasses() {
        ServiceLoader<JsonHandler> loader = ServiceLoader.load(JsonHandler.class);
        for (JsonHandler jsonHandler : loader) {
            Class<? extends JsonHandler> clazz = jsonHandler.getClass();
            JsonProviderName jsonProviderName = clazz.getAnnotation(JsonProviderName.class);
            if (jsonProviderName == null) {
                log.warn("JSON Handler " + clazz + " JsonProviderName annotation is missing");
                continue;
            }
            String name = jsonProviderName.value();
            ALL.put(name, jsonHandler);
        }

        String jsonProviderValue = null;
//        InputStream in = JsonHandler.class.getClassLoader().getResourceAsStream("sjf4j-api.properties");
//        if (in != null) {
//            Properties properties = new Properties();
//            try {
//                properties.load(in);
//                jsonProviderValue = properties.getProperty(JsonProviderName.class.getName());
//            } catch (IOException e) {
//                log.warn("Loading Properties[sjf4j-api.properties] failed", e);
//            }
//        }
        if (Strings.isBlank(jsonProviderValue)) {
            TreeMap<Integer, String> treeMap = new TreeMap<>();
            ALL.values().forEach(item -> {
                JsonProviderName jsonProviderName = item.getClass().getAnnotation(JsonProviderName.class);
                treeMap.put(jsonProviderName.index(), jsonProviderName.value());
            });
            jsonProviderValue = treeMap.firstEntry().getValue();
        }
        if (Strings.isNotBlank(jsonProviderValue)) {
            defaultJsonHandler = ALL.get(jsonProviderValue);
        }
    }

    /**
     * 返回默认的{@linkplain  JsonHandler}
     * 优先从判断classpath是否有配置文件fast-json-api.properties
     * 其次根据每个实现{@linkplain  JsonHandler}引擎的注解{@linkplain  JsonProviderName#index()},值小的将作为默认处理器
     *
     * @return {@linkplain JsonHandler}
     */
    public static JsonHandler defaultJsonHandler() {
        assert defaultJsonHandler != null;
        return defaultJsonHandler;
    }

    /**
     * 返回指定的JSON引擎的实现,如果不存在返回默认{@linkplain  #defaultJsonHandler}
     *
     * @param jsonProviderValue JSON引擎名称
     * @return {@linkplain JsonHandler}
     */
    public static JsonHandler jsonHandler(String jsonProviderValue) {
        return ALL.getOrDefault(jsonProviderValue, defaultJsonHandler());
    }

}
