package cn.tmkit.http.shf4j;

import cn.tmkit.core.io.Files;
import cn.tmkit.core.lang.CollectionUtils;
import cn.tmkit.core.lang.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 含文件的表单
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class MultipartBody extends RequestBody {

    private final String boundary;

    private final List<Part> parts;

    MultipartBody(Builder builder) {
        this.contentType = builder.contentType;
        this.boundary = builder.boundary;
        this.parts = Collections.unmodifiableList(builder.parts);
    }

    public String getBoundary() {
        return boundary;
    }

    public List<Part> getParts() {
        return parts;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Part {

        private final String name;

        private String value;

        private final ContentType contentType;

        private RequestBody body;

        private File file;

        private InputStream in;

        Part(String name, String value, ContentType contentType) {
            this.name = name;
            this.value = value;
            this.contentType = contentType;
        }

        Part(String name, RequestBody value, ContentType contentType) {
            this.name = name;
            this.body = value;
            this.contentType = contentType;
        }

        Part(String name, String value, RequestBody body, ContentType contentType) {
            this(name, value, contentType);
            this.body = body;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        @NotNull
        public ContentType getContentType() {
            return contentType;
        }

        public RequestBody getBody() {
            return body;
        }

        @Nullable
        public File getFile() {
            return file;
        }

        @Nullable
        public InputStream getIn() {
            return in;
        }

        public static Part create(String name, String value) {
            return create(name, value, (ContentType) null);
        }

        public static Part create(String name, String value, ContentType contentType) {
            return new Part(name, value, contentType == null ? ContentType.DEFAULT_TEXT : contentType);
        }

        public static Part create(String name, @NotNull File file) {
            return create(name, file.getName(), file);
        }

        public static Part create(String name, String filename, @NotNull File file) {
            Objects.requireNonNull(file, "file == null");
            Part part = new Part(name, filename, ContentType.parseByFileExt(Files.getFileExt(file)));
            part.file = file;
            return part;
        }

        public static Part create(String name, String filename, InputStream in) {
            return create(name, filename, in, null);
        }

        public static Part create(String name, String filename, @NotNull InputStream in, ContentType contentType) {
            Part part = new Part(name, filename, contentType == null ? ContentType.DEFAULT_BINARY : contentType);
            part.in = in;
            return part;
        }

        public static Part create(String name, String filename, RequestBody body) {
            return create(name, filename, body, null);
        }

        public static Part create(String name, String filename, RequestBody body, ContentType contentType) {
            Part part = new Part(name, filename, contentType == null ? ContentType.DEFAULT_BINARY : contentType);
            part.body = body;
            return part;
        }

    }

    public static class Builder {

        private String boundary;

        private ContentType contentType;

        private final List<Part> parts = new ArrayList<>();

        public Builder() {
            this.contentType = ContentType.MULTIPART_FORM_DATA;
        }

        public Builder boundary(String boundary) {
            this.contentType = ContentType.MULTIPART_FORM_DATA;
            this.boundary = boundary;
            return this;
        }

        public Builder contentType(@NotNull ContentType contentType) {
            this.contentType = Objects.requireNonNull(contentType, "contentType == null");
            return this;
        }

        public Builder add(String name, String value) {
            return add(name, value, (ContentType) null);
        }

        public Builder add(String name, List<String> values) {
            if (CollectionUtils.isNotEmpty(values)) {
                values.forEach(value -> add(name, value));
            }
            return this;
        }

        public Builder add(String name, String value, ContentType contentType) {
            if (Strings.hasLength(name)) {
                parts.add(Part.create(name, value, contentType));
            }
            return this;
        }

        public Builder add(String name, File file) {
            if (Strings.hasLength(name) && file != null) {
                parts.add(Part.create(name, file));
            }
            return this;
        }

        public Builder add(String name, String filename, RequestBody body) {
            if (Strings.isAllNotBlank(name, filename) && body != null) {
                parts.add(Part.create(name, filename, body));
            }
            return this;
        }

        public Builder add(@NotNull Part part) {
            parts.add(Objects.requireNonNull(part, "part == null"));
            return this;
        }

        public MultipartBody build() {
            return new MultipartBody(this);
        }

    }

}
