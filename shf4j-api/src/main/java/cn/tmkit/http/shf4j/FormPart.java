package cn.tmkit.http.shf4j;

import cn.tmkit.core.io.FileUtil;
import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 表单字段
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2024-01-29
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class FormPart {


    /**
     * 表单字段名
     */
    private final String name;

    /**
     * 表单字段值的类型
     */
    @NotNull
    private final ContentType contentType;

    /**
     * 表单字段值的输入流
     */
    private final InputStream in;

    /**
     * 文件名，仅文件上传
     */
    private String filename;

    /**
     * 创建普通表单字段
     *
     * @param name  表单字段名
     * @param value 表单字段值
     * @return {@linkplain FormPart}
     */
    public static FormPart create(String name, String value) {
        return create(name, value, (ContentType) null);
    }

    /**
     * 创建普通表单字段
     *
     * @param name        表单字段名
     * @param value       表单字段值
     * @param contentType 表单字段值的类型
     * @return {@linkplain FormPart}
     */
    public static FormPart create(String name, String value, ContentType contentType) {
        ContentType targetContentType = Objects.getIfNull(contentType, ContentType.DEFAULT_TEXT);
        Charset encoding = Objects.getIfNull(targetContentType.getCharset(), Charsets.UTF_8);
        return new FormPart(name, targetContentType, new ByteArrayInputStream(value.getBytes(encoding)));
    }

    /**
     * 创建普通表单字段
     *
     * @param name        表单字段名
     * @param value       表单字段值
     * @param contentType 表单字段值的类型
     * @return {@linkplain FormPart}
     */
    public static FormPart create(String name, byte[] value, ContentType contentType) {
        return new FormPart(name, Objects.getIfNull(contentType, ContentType.DEFAULT_TEXT), new ByteArrayInputStream(value));
    }

    /**
     * 创建文件表单字段
     *
     * @param name 表单字段名
     * @param file 文件
     * @return {@literal @FormPart}
     */
    public static FormPart create(String name, @NotNull File file) {
        return new FormPart(name, ContentType.parseByFileExt(FileUtil.getFileExt(file)),
                FileUtil.getBufferedInputStream(file), file.getName());
    }

    /**
     * 创建文件表单字段
     *
     * @param name     表单字段名
     * @param filename 文件名
     * @param in       文件输入流
     * @return {@literal @FormPart}
     */
    public static FormPart create(String name, @NotNull String filename, @NotNull InputStream in) {
        return create(name, filename, in, null);
    }

    /**
     * 创建文件表单字段
     *
     * @param name        表单字段名
     * @param filename    文件名
     * @param in          文件输入流
     * @param contentType 文件类型，如果为{@code null}则为{@linkplain ContentType#DEFAULT_BINARY}
     * @return {@literal @FormPart}
     */
    public static FormPart create(String name, @NotNull String filename, @NotNull InputStream in, @Nullable ContentType contentType) {
        return new FormPart(name, Objects.getIfNull(contentType, ContentType.DEFAULT_BINARY), in, filename);
    }

    public static FormPart create(String name, @NotNull FormPart formPart) {
        return new FormPart(name, formPart.contentType, formPart.in, formPart.filename);
    }


}
