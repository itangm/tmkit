package cn.tmkit.http.shf4j;

import cn.tmkit.core.io.Files;
import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.Charsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * HttpRequest Body
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class RequestBody {

    /**
     * 请求的内容类型
     */
    protected ContentType contentType;

    /**
     * 请求的内容数据
     */
    protected byte[] data;

    protected RequestBody() {
        this.contentType = ContentType.ALL;
    }

    protected RequestBody(ContentType contentType, byte[] data) {
        this.contentType = contentType;
        this.data = data;
    }

    /**
     * 请求内容的长度，如果未知则返回{@code null}
     *
     * @return 内容长度
     */
    @Nullable
    public Integer contentLength() {
        return data != null ? data.length : null;
    }

    /**
     * 返回请求中Content-Type的请求头
     *
     * @return Content-Type header
     */
    @Nullable
    public ContentType contentType() {
        return contentType;
    }

    /**
     * 请求内容
     *
     * @return 内容
     */
    public byte[] getData() {
        return data;
    }

    /**
     * 返回编码
     *
     * @return 编码，可能为{@code null}
     */
    public Charset getCharset() {
        return contentType != null ? contentType.getCharset() : Charsets.UTF_8;
    }

    /**
     * 从文本内容生成请求体对象
     *
     * @param contentType 请求类型
     * @param content     请求体的内容
     * @return {@linkplain RequestBody}对象
     */
    public static RequestBody create(ContentType contentType, @NotNull String content) {
        Charset charset = Charsets.UTF_8;
        if (contentType != null) {
            charset = contentType.getCharset();
            if (charset == null) {
                charset = Charsets.UTF_8;
                contentType = ContentType.parse(contentType + "; charset=utf-8");
            }
        }
        byte[] data = content.getBytes(charset);
        return new RequestBody(contentType, data);
    }

    /**
     * 通用的请求体对象生成
     *
     * @param contentType 请求类型
     * @param data        请求体的内容
     * @return {@linkplain RequestBody}对象
     */
    public static RequestBody create(ContentType contentType, byte[] data) {
        return new RequestBody(contentType, data);
    }

    /**
     * 从文件内容生成请求体对象
     *
     * @param contentType 请求类型
     * @param file        文件内容
     * @return {@linkplain RequestBody}对象
     */
    public static RequestBody create(ContentType contentType, @NotNull File file) {
        return new RequestBody(contentType, Files.readBytes(file));
    }

    /**
     * 从输入流生成请求体对象
     *
     * @param contentType 请求类型
     * @param in          文件内容
     * @return {@linkplain RequestBody}对象
     */
    public static RequestBody create(ContentType contentType, @NotNull InputStream in) {
        return new RequestBody(contentType, IoUtil.readBytes(in));
    }

}
