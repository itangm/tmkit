package cn.tmkit.http.shf4j;

import cn.tmkit.core.exception.IoRuntimeException;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * HTTP响应内容接口
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public interface ResponseBody extends Closeable, AutoCloseable {

    /**
     * 字节的长度，可能为{@code null}
     *
     * @return 字节长度
     */
    @Nullable
    Integer length();

    /**
     * 是否支持重复读取，如果为{@code true}则 {@link #byteStream()} 和 {@link #asReader(Charset)} 可以被多次调用
     */
    boolean isRepeatable();

    /**
     * 获得响应内容的流
     *
     * @throws IoRuntimeException 流操作异常
     */
    InputStream byteStream() throws IoRuntimeException;

    /**
     * 将响应内容按照指定编码{@code charset}转为对应的流
     *
     * @param charset 编码，可空
     */
    Reader asReader(@Nullable Charset charset) throws IoRuntimeException;

    /**
     * 将响应内容转为字符串，并且会自动关闭流
     *
     * @param charset 编码字符集，可空，
     * @return 响应字符串
     * @throws IoRuntimeException IO异常
     */
    String string(@Nullable Charset charset) throws IoRuntimeException;

}
