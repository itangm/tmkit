package cn.tmkit.http.shf4j;

import cn.tmkit.core.lang.Asserts;
import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Collections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Form Body
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public final class FormBody extends RequestBody {

    private final List<NameValuePair> nameValuePairs;
    private final Charset charset;

    FormBody(List<NameValuePair> nameValuePairs, Charset charset) {
        this.nameValuePairs = nameValuePairs;
        this.contentType = ContentType.APPLICATION_FORM_URLENCODED;
        this.charset = charset;
    }

    public List<NameValuePair> getItems() {
        return nameValuePairs;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@linkplain FormBody}的构建类
     *
     * @author miles.tang
     * @version $Revision
     */
    public static class Builder {

        /**
         * name-value pairs
         */
        private final List<NameValuePair> nameValuePairs = Collections.arrayList();

        /**
         * 字符集
         */
        @Nullable
        private final Charset charset;

        public Builder() {
            this(null);
        }

        public Builder(Charset charset) {
            this.charset = Charsets.getCharset(charset, Charsets.UTF_8);
        }

        public Builder add(@NotNull String name, @NotNull String value) {
            nameValuePairs.add(new NameValuePair(Asserts.notNull(name, "name must not be null"),
                    Asserts.notNull(value, "value must not be null")));
            return this;
        }

        public Builder add(@NotNull String name, @NotNull List<String> values) {
            Asserts.notNull(name, "name must not be null");
            if (Collections.isNotEmpty(values)) {
                values.forEach(value -> add(name, value));
            }
            return this;
        }

        public FormBody build() {
            return new FormBody(nameValuePairs, charset);
        }
    }

    /**
     * 参数名和参数值的一对
     *
     * @author miles.tang
     * @version $Revision
     */
    public static class NameValuePair {

        private final String name;

        private final String value;

        NameValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

    }

}
