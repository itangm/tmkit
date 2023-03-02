package cn.tmkit.http.shf4j;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Client工厂
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
@Slf4j
public class Factory {

    private static ClientBuilder defaultClientBuilder;

    private static final Map<String, ClientBuilder> allRegistryClientBuilder = new HashMap<>();

    static {
        findClientBuilderImplClasses();
    }

    private static void findClientBuilderImplClasses() {
        ServiceLoader<ClientBuilder> loader = ServiceLoader.load(ClientBuilder.class);
        loader.forEach(clientBuilder -> {
            Class<? extends ClientBuilder> clazz = clientBuilder.getClass();
            String name = parseName(clazz);
            log.info("Registry a client builder {} for {}", name, clazz.getCanonicalName());
            allRegistryClientBuilder.put(name, clientBuilder);
            if (defaultClientBuilder == null) {
                defaultClientBuilder = clientBuilder;
            }
        });
        if (defaultClientBuilder == null) {
            defaultClientBuilder = new ClientBuilder.Default();
        }
    }

    private static String parseName(Class<? extends ClientBuilder> clazz) {
        ClientProvider clientProvider = clazz.getAnnotation(ClientProvider.class);
        if (clientProvider != null) {
            String nameStr = clientProvider.value();
            nameStr = nameStr.trim();
            if (!nameStr.isEmpty()) {
                return nameStr;
            }
        }
        return clazz.getName();
    }

    public static ClientBuilder get() {
        return defaultClientBuilder;
    }

    public static ClientBuilder get(String name) {
        return allRegistryClientBuilder.get(name);
    }

}
