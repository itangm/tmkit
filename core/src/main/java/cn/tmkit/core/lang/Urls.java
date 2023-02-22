package cn.tmkit.core.lang;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.exception.UriSyntaxRuntimeException;
import cn.tmkit.core.map.LinkedMultiValueMap;
import cn.tmkit.core.map.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.jar.JarFile;

/**
 * URL相关的工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class Urls {

    /**
     * url编码
     *
     * @param text     待编码的内容
     * @param encoding 编码
     * @return 编码后的url
     */
    public static String encode(final CharSequence text, String encoding) {
        return encode(text, Charsets.forName(encoding));
    }

    /**
     * url编码，字符集采用{@code UTF-8}
     *
     * @param text 待编码的内容
     * @return 编码后的url
     */
    public static String encode(final CharSequence text) {
        return encode(text, Charsets.UTF_8);
    }

    /**
     * url编码，指定字符集
     *
     * @param text    待编码的内容
     * @param charset 指定字符集
     * @return 编码后的url
     */
    public static String encode(final CharSequence text, Charset charset) {
        if (Strings.isEmpty(text)) {
            return Strings.EMPTY_STRING;
        }
        if (charset == null) {
            charset = Charsets.UTF_8;
        }
        try {
            return URLEncoder.encode(text.toString(), charset.name());
        } catch (UnsupportedEncodingException e) {
            //never happen
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * URL解码，字符集采用{@code UTF-8}
     *
     * @param text 待解码的内容
     * @return 解码后的url
     */
    public static String decode(final CharSequence text) {
        return decode(text, Charsets.UTF_8);
    }

    /**
     * URL解码，指定字符集
     *
     * @param text    待解码的内容
     * @param charset 指定字符集
     * @return 解码后的url
     */
    public static String decode(final CharSequence text, Charset charset) {
        if (Strings.isEmpty(text)) {
            return Strings.EMPTY_STRING;
        }
        if (charset == null) {
            charset = Charsets.UTF_8;
        }
        try {
            return URLDecoder.decode(text.toString(), charset.name());
        } catch (UnsupportedEncodingException e) {
            //never happen
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * <p>将url中的请求参数转为Map对象</p>
     *
     * @param urlParamStr url请求参数
     * @return 返回Map对象
     */
    public static MultiValueMap<String, String> parseByUrlQueryStr(CharSequence urlParamStr) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        if (urlParamStr.length() == 0) {
            return multiValueMap;
        }
        List<String> urlParamList = Strings.splitToList(urlParamStr, Strings.AMP);
        if (Collections.isEmpty(urlParamList)) {
            return multiValueMap;
        }

        String[] array;
        for (String urlParam : urlParamList) {
            if (Strings.hasText(urlParam)) {
                array = Strings.split(urlParam, Strings.EQUALS, true, true);
                if (array.length == 2) {
                    multiValueMap.add(array[0], decode(array[1]));
                } else {
                    multiValueMap.add(array[0], null);
                }
            }
        }
        return multiValueMap;
    }

    /**
     * Map参数转为Url参数，默认采用{@code UTF-8}编码
     *
     * @param params 参数
     * @return 符合URL规范的参数
     */
    public static String generateByUrlQueryString(Map<? extends CharSequence, ?> params) {
        return generateByUrlQueryString(params, null);
    }

    /**
     * Map参数转为Url参数
     *
     * @param params   参数
     * @param encoding 编码，可以为空，默认是{@code UTF-8}
     * @return 符合URL规范的参数
     */
    public static String generateByUrlQueryString(Map<? extends CharSequence, ?> params, Charset encoding) {
        if (Maps.isEmpty(params)) {
            return Strings.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        final Charset charset = Charsets.getCharset(encoding, Charsets.UTF_8);
        params.forEach((BiConsumer<CharSequence, Object>) (cs, val) -> {
            if (Strings.isEmpty(cs) || val == null) {
                return;
            }
            if (val instanceof Iterable) {
                ((Iterable<?>) val).forEach((Consumer<Object>) value -> appendNameAndValue(builder, cs, value, charset));
            } else if (val instanceof Iterator<?>) {
                ((Iterator<?>) val).forEachRemaining((Consumer<Object>) value -> appendNameAndValue(builder, cs, value, charset));
            } else {
                appendNameAndValue(builder, cs, val, charset);
            }
        });
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private static void appendNameAndValue(StringBuilder builder, CharSequence name, Object value, Charset charset) {
        builder.append(name.toString())
                .append(Strings.EQUALS)
                .append(encode(value.toString(), charset))
                .append(Strings.AMP);
    }

    /**
     * 为url增加请求参数
     *
     * @param url   原始的请求地址
     * @param name  参数名
     * @param value 参数值
     * @return 返回追加了参数的地址
     */
    public static String addParam(String url, String name, String value) {
        return addParam(url, name, value, null);
    }

    /**
     * 为url增加请求参数
     *
     * @param url      原始的请求地址
     * @param name     参数名
     * @param value    参数值
     * @param encoding 编码
     * @return 返回追加了参数的地址
     */
    public static String addParam(CharSequence url, CharSequence name, CharSequence value, String encoding) {
        if (Strings.isEmpty(url)) {
            return Strings.EMPTY_STRING;
        }
        String str = url.toString();
        if (Strings.isEmpty(name) || Strings.isEmpty(value)) {
            return str;
        }
        if (encoding == null) {
            encoding = Charsets.UTF_8_VALUE;
        }
        String queryString = name + "=" + encode(value, encoding);
        if (str.contains("?")) {
            return str + "&" + queryString;
        } else {
            return str + "?" + queryString;
        }
    }

    /**
     * 为url增加请求参数
     *
     * @param url    原始的请求地址
     * @param params 参数
     * @return 返回追加了参数的地址
     */
    public static String addParams(CharSequence url, Map<? extends CharSequence, ?> params) {
        return addParams(url, params, null);
    }

    /**
     * 为url增加请求参数
     *
     * @param url      原始的请求地址
     * @param params   参数
     * @param encoding 编码
     * @return 返回追加了参数的地址
     */
    public static String addParams(CharSequence url, Map<? extends CharSequence, ?> params, String encoding) {
        if (Strings.isEmpty(url)) {
            return Strings.EMPTY_STRING;
        }
        if (Maps.isEmpty(params)) {
            return url.toString();
        }
        String str = url.toString();
        String queryString = generateByUrlQueryString(params, Charsets.forName(encoding));
        if (str.contains("?")) {
            return str + "&" + queryString;
        } else {
            return str + "?" + queryString;
        }
    }

    /**
     * 从URL中获取流
     *
     * @param url {@link URL}
     * @return InputStream流
     */
    public static InputStream openStream(URL url) {
        Asserts.notNull(url);
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 获得URL，常用于使用绝对路径时的情况
     *
     * @param file URL对应的文件对象
     * @return URL
     * @throws IoRuntimeException MalformedURLException
     */
    public static URL getUrl(File file) {
        Asserts.notNull(file);
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 从URL中获取JarFile
     *
     * @param url url
     * @return JarFile
     */
    public static JarFile getJarFile(URL url) {
        Asserts.notNull(url);
        try {
            return ((JarURLConnection) url.openConnection()).getJarFile();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 将{@linkplain URL}转为{@linkplain URI}
     *
     * @param url url instance
     * @return URI实例
     */
    public static URI toUri(URL url) throws UriSyntaxRuntimeException {
        return toUri(url.toString());
    }

    /**
     * 根据路径位置创建{@linkplain URI}实例
     *
     * @param location 路径位置
     * @return URI实例
     */
    public static URI toUri(String location) throws UriSyntaxRuntimeException {
        try {
            return new URI(Strings.replace(location, " ", "%20"));
        } catch (URISyntaxException e) {
            throw new UriSyntaxRuntimeException(e);
        }
    }

    /**
     * 验证是否为URL
     *
     * @param value 值
     * @return 是否为URL
     */
    public static boolean isUrl(CharSequence value) {
        if (Strings.isEmpty(value)) {
            return false;
        }
        try {
            new URL(Strings.str(value));
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }


}
