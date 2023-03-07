package cn.tmkit.test.apiserver.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置项
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.config")
public class AppConfigProperties {

    /**
     * IP查询地址
     */
    private String ipApi;

}
