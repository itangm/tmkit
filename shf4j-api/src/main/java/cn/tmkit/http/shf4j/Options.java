package cn.tmkit.http.shf4j;

import cn.tmkit.core.lang.Asserts;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <a href="https://github.com/Arronlong/httpclientutil/blob/master/src/main/java/com/arronlong/httpclientutil/builder/HCB.java">HCB.java</a>
 * <a href="https://gitee.com/m310851010/httpkit/blob/master/pom.xml">httpkit</a>
 * 配置项
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class Options {

    /**
     * 连接超时时间,单位毫秒
     */
    private final int connectTimeoutMillis;

    /**
     * 读取超时时间,单位毫秒
     */
    private final int readTimeoutMillis;

    /**
     * 写出超时时间,单位毫秒
     */
    private final int writeTimeoutMillis;

    /**
     * 是否支持3xx跳转
     */
    private final boolean followRedirects;

    /**
     * 重试（如果请求是幂等的，就再次尝试）
     */
    private final int retryCount;

    /**
     * 代理
     */
    private final ProxyInfo proxyInfo;

    private final List<HttpStatus> decodeStatusCodes;

    private SSLConfig sslConfig;

    Options(Builder builder) {
        this.connectTimeoutMillis = builder.connectTimeoutMillis;
        this.readTimeoutMillis = builder.readTimeoutMillis;
        this.writeTimeoutMillis = builder.writeTimeoutMillis;
        this.followRedirects = builder.followRedirects;
        this.retryCount = builder.retryCount;
        this.proxyInfo = builder.proxyInfo;
        this.decodeStatusCodes = builder.decodeStatusCodes;
    }

    public int connectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public int readTimeoutMillis() {
        return readTimeoutMillis;
    }

    public int writeTimeoutMillis() {
        return writeTimeoutMillis;
    }

    public boolean followRedirects() {
        return followRedirects;
    }

    public int retryCount() {
        return retryCount;
    }

    public ProxyInfo proxyInfo() {
        return proxyInfo;
    }

    public List<HttpStatus> decodeStatusCodes() {
        return decodeStatusCodes;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class SSLConfig {

    }

    public static class Builder {

        /**
         * 连接超时时间,单位毫秒
         */
        private int connectTimeoutMillis;

        /**
         * 读取超时时间,单位毫秒
         */
        private int readTimeoutMillis;

        /**
         * 写出超时时间,单位毫秒
         */
        private int writeTimeoutMillis;

        /**
         * 是否支持3xx跳转
         */
        private boolean followRedirects;

        /**
         * 重试（如果请求是幂等的，就再次尝试）
         */
        private int retryCount;

        /**
         * 代理
         */
        private ProxyInfo proxyInfo;

        private List<HttpStatus> decodeStatusCodes;

        Builder() {
            super();
        }

        Builder(@NotNull Options source) {
            this.connectTimeoutMillis = Objects.requireNonNull(source).connectTimeoutMillis;
            this.readTimeoutMillis = source.readTimeoutMillis;
            this.writeTimeoutMillis = source.connectTimeoutMillis;
            this.followRedirects = source.followRedirects;
            this.retryCount = source.retryCount;
            this.proxyInfo = source.proxyInfo;
        }

        /**
         * 设置连接超时时间，单位毫秒
         *
         * @param connectTimeoutMillis 连接超时毫秒数
         * @return {@code Builder}
         */
        public Builder connectTimeoutMillis(int connectTimeoutMillis) {
            Asserts.isTrue(connectTimeoutMillis >= 0, "'connectTimeoutMillis' must than 0");
            this.connectTimeoutMillis = connectTimeoutMillis;
            return this;
        }

        /**
         * 设置连接超时时间，单位毫秒
         *
         * @param connectTimeoutMillis 连接超时毫秒数
         * @return {@code Builder}
         */
        public Builder connectTimeoutMillis( Integer connectTimeoutMillis) {
            if (connectTimeoutMillis != null) {
                return connectTimeoutMillis(connectTimeoutMillis.intValue());
            }
            return this;
        }

        /**
         * 设置连接超时时间
         *
         * @param connectTimeout 连接超时时间
         * @param timeUnit       超时单位
         * @return {@code Builder}
         */
        public Builder connectTimeout(int connectTimeout, @NotNull TimeUnit timeUnit) {
            Asserts.isTrue(connectTimeoutMillis >= 0, "'connectTimeout' must than 0");
            this.connectTimeoutMillis = (int) timeUnit.toMillis(connectTimeout);
            return this;
        }

        /**
         * 设置连接超时时间
         *
         * @param connectTimeout 连接超时时间
         * @param timeUnit       超时单位
         * @return {@code Builder}
         */
        public Builder connectTimeout( Integer connectTimeout,  TimeUnit timeUnit) {
            if (connectTimeout != null) {
                return connectTimeout(connectTimeout.intValue(), timeUnit);
            }
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param readTimeoutMillis 读取数据超时时间，时间单位为毫秒
         * @return {@code Builder}
         */
        public Builder readTimeoutMillis(int readTimeoutMillis) {
            Asserts.isTrue(readTimeoutMillis >= 0, "'readTimeoutMillis' must than 0");
            this.readTimeoutMillis = readTimeoutMillis;
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param readTimeoutMillis 读取数据超时时间，时间单位为毫秒
         * @return {@code Builder}
         */
        public Builder readTimeoutMillis(Integer readTimeoutMillis) {
            if (readTimeoutMillis != null) {
                return readTimeoutMillis(readTimeoutMillis.intValue());
            }
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param readTimeout 读取数据超时时间
         * @param timeUnit    时间单位
         * @return {@code Builder}
         */
        public Builder readTimeout(int readTimeout, @NotNull TimeUnit timeUnit) {
            Asserts.isTrue(readTimeout >= 0, "'readTimeout' must than 0");
            this.readTimeoutMillis = (int) timeUnit.toMillis(readTimeout);
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param readTimeout 读取数据超时时间
         * @param timeUnit    时间单位
         * @return {@code Builder}
         */
        public Builder readTimeout( Integer readTimeout,  TimeUnit timeUnit) {
            if (readTimeout != null) {
                Asserts.notNull(timeUnit);
                return readTimeout(readTimeout.intValue(), timeUnit);
            }
            return this;
        }

        /**
         * 写出的超时时间
         *
         * @param writeTimeoutMillis 超时毫秒数
         * @return {@code Builder}
         */
        public Builder writeTimeoutMillis(int writeTimeoutMillis) {
            Asserts.isTrue(writeTimeoutMillis >= 0, "'writeTimeoutMillis' must than 0");
            this.writeTimeoutMillis = writeTimeoutMillis;
            return this;
        }

        /**
         * 写出的超时时间
         *
         * @param writeTimeoutMillis 超时毫秒数
         * @return {@code Builder}
         */
        public Builder writeTimeoutMillis(Integer writeTimeoutMillis) {
            if (writeTimeoutMillis != null) {
                return writeTimeoutMillis(writeTimeoutMillis.intValue());
            }
            return this;
        }

        /**
         * 写出的超时时间
         *
         * @param writeTimeout 超时毫秒数
         * @param timeUnit     时间单位
         * @return {@code Builder}
         */
        public Builder writeTimeout(int writeTimeout, @NotNull TimeUnit timeUnit) {
            Asserts.isTrue(writeTimeout >= 0, "'writeTimeout' must than 0");
            this.writeTimeoutMillis = (int) timeUnit.toMillis(writeTimeout);
            return this;
        }

        /**
         * 写出的超时时间
         *
         * @param writeTimeout 超时
         * @param timeUnit     时间单位
         * @return {@code Builder}
         */
        public Builder writeTimeout(Integer writeTimeout, @NotNull TimeUnit timeUnit) {
            if (writeTimeout != null) {
                return writeTimeout(writeTimeout.intValue(), timeUnit);
            }
            return this;
        }

        public Builder followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        /**
         * 设置重试的次数，0则不会重置
         *
         * @param retryCount 重试次数，必须不小于0
         * @return {@linkplain Builder}
         */
        public Builder retryCount(int retryCount) {
            Asserts.isTrue(retryCount >= 0, "'retryCount' must than 0");
            this.retryCount = retryCount;
            return this;
        }

        /**
         * 设置HTTP代理信息
         *
         * @param hostOrIp 代理主机或IP
         * @param port     代理端口
         * @return {@linkplain Builder}
         */
        public Builder httpProxy(@NotNull String hostOrIp, int port) {
            Asserts.notEmpty(hostOrIp, "'hostOrIp' must not be null or empty");
            Asserts.isTrue(port > 0 && port <= 65535, "port between 1 and 65535");
            this.proxyInfo = ProxyInfo.http(hostOrIp, port);
            return this;
        }

        public Builder decodeStatusCodes(List<HttpStatus> decodeStatusCodes) {
            this.decodeStatusCodes = decodeStatusCodes;
            return this;
        }

        public Options build() {
            return new Options(this);
        }

    }

    /**
     * 默认的配置，连接、读取，写入为60s超时
     */
    public static Options DEFAULT_OPTIONS = new Builder()
            .connectTimeoutMillis(60_000)
            .readTimeoutMillis(60_000)
            .writeTimeoutMillis(60_000)
            .followRedirects(true)
            .retryCount(0)
            .build();

}
