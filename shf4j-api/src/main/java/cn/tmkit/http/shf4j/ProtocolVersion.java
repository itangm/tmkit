package cn.tmkit.http.shf4j;

/**
 * HTTP协议版本
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public enum ProtocolVersion {

    /**
     * HTTP/1.0 protocol
     */
    HTTP_1_0("HTTP/1.0"),

    /**
     * HTTP/1.1 protocol
     */
    HTTP_1_1("HTTP/1.1"),

    /**
     * HTTP/2.0 protocol
     */
    HTTP_2("HTTP/2.0"),

    /**
     * MOCK
     */
    MOCK;

    private final String protocolVersion;

    ProtocolVersion() {
        protocolVersion = name();
    }

    ProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public String toString() {
        return protocolVersion;
    }

}
