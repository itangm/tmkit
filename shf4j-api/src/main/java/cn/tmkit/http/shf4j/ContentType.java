package cn.tmkit.http.shf4j;

import cn.tmkit.core.lang.Asserts;
import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Strings;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HTTP请求头和响应头
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class ContentType implements java.io.Serializable {

    private static final long serialVersionUID = 2023L;

    private static final String PARAM_CHARSET = "charset";

    // constants
    public static final ContentType ALL;
    public static final ContentType TEXT_XML;
    public static final ContentType TEXT_PLAIN;
    public static final ContentType TEXT_HTML;
    public static final ContentType TEXT_MARKDOWN;

    // form or multipart
    public static final ContentType MULTIPART_FORM_DATA;
    public static final ContentType MULTIPART_MIXED;
    public static final ContentType APPLICATION_FORM_URLENCODED;
    public static final ContentType APPLICATION_OCTET_STREAM;

    // application
    public static final ContentType APPLICATION_JSON;
    public static final ContentType APPLICATION_XML;
    public static final ContentType APPLICATION_HTML;
    public static final ContentType APPLICATION_JAVASCRIPT;

    // image
    public static final ContentType IMAGE_PNG;
    public static final ContentType IMAGE_JPEG;
    public static final ContentType IMAGE_GIF;

    // zip
    public static final ContentType APPLICATION_ZIP;
    public static final ContentType APPLICATION_GZ;

    // pdf
    public static final ContentType APPLICATION_PDF;

    // ms
    public static final ContentType APPLICATION_DOC;
    public static final ContentType APPLICATION_XLS;
    public static final ContentType APPLICATION_PPT;

    // apk or ios
    public static final ContentType APPLICATION_APK;
    public static final ContentType APPLICATION_IPA;

    // defaults
    public static final ContentType DEFAULT_TEXT;
    public static final ContentType DEFAULT_BINARY;

    // support file extension
    private static final Map<String, ContentType> SUPPORT_FILE_EXTS;

    static {
        ALL = new ContentType("*", "*", null);

        TEXT_PLAIN = new ContentType("text", "plain", Charsets.UTF_8);
        TEXT_XML = new ContentType("text", "xml", Charsets.UTF_8);
        TEXT_HTML = new ContentType("text", "html", Charsets.UTF_8);
        TEXT_MARKDOWN = new ContentType("text", "markdown", Charsets.UTF_8);

        MULTIPART_FORM_DATA = new ContentType("multipart", "form-data", Charsets.UTF_8);
        MULTIPART_MIXED = new ContentType("multipart", "mixed", Charsets.UTF_8);
        APPLICATION_FORM_URLENCODED = new ContentType("application", "x-www-form-urlencoded", Charsets.UTF_8);

        APPLICATION_OCTET_STREAM = new ContentType("application", "octet-stream", null);
        APPLICATION_JSON = new ContentType("application", "json", Charsets.UTF_8);
        APPLICATION_XML = new ContentType("application", "xml", null);
        APPLICATION_HTML = new ContentType("application", "html", null);
        APPLICATION_JAVASCRIPT = new ContentType("application", "javascript", null);

        IMAGE_PNG = new ContentType("image", "png", null);
        IMAGE_GIF = new ContentType("image", "gif", null);
        IMAGE_JPEG = new ContentType("image", "jpeg", null);

        APPLICATION_ZIP = new ContentType("application", "zip", null);
        APPLICATION_GZ = new ContentType("application", "x-gzip", null);

        APPLICATION_PDF = new ContentType("application", "pdf", null);

        APPLICATION_DOC = new ContentType("application", "msword", null);
        APPLICATION_XLS = new ContentType("application", "vnd.ms-excel", null);
        APPLICATION_PPT = new ContentType("application", "vnd.ms-powerpoint", null);
        APPLICATION_APK = new ContentType("application", "vnd.android.package-archive", null);
        APPLICATION_IPA = new ContentType("application", "iphone", null);

        DEFAULT_TEXT = TEXT_PLAIN;
        DEFAULT_BINARY = APPLICATION_OCTET_STREAM;

        Map<String, ContentType> _supportFileExts = new ConcurrentHashMap<>();
        _supportFileExts.put("json", APPLICATION_JSON);
        _supportFileExts.put("doc", APPLICATION_DOC);
        _supportFileExts.put("docx", APPLICATION_DOC);
        _supportFileExts.put("xls", APPLICATION_XLS);
        _supportFileExts.put("xlsx", APPLICATION_XLS);
        _supportFileExts.put("ppt", APPLICATION_PPT);
        _supportFileExts.put("pptx", APPLICATION_PPT);
        _supportFileExts.put("pdf", APPLICATION_PDF);
        _supportFileExts.put("zip", APPLICATION_ZIP);
        _supportFileExts.put("gzip", APPLICATION_GZ);
        _supportFileExts.put("png", IMAGE_PNG);
        _supportFileExts.put("jpeg", IMAGE_JPEG);
        _supportFileExts.put("jpg", IMAGE_JPEG);
        _supportFileExts.put("gif", IMAGE_GIF);
        _supportFileExts.put("html", TEXT_HTML);
        _supportFileExts.put("txt", TEXT_PLAIN);
        _supportFileExts.put("apk", APPLICATION_APK);
        _supportFileExts.put("ipa", APPLICATION_IPA);

        SUPPORT_FILE_EXTS = Collections.unmodifiableMap(_supportFileExts);
    }

    private final String type;

    private final String subtype;

    private final Charset charset;

    ContentType(String type, String subtype, Charset charset) {
        this.type = type;
        this.subtype = subtype;
        this.charset = charset;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public Charset getCharset() {
        return charset;
    }

    public ContentType charset(Charset charset) {
        return new ContentType(type, subtype, charset);
    }

    /**
     * 返回MIME Type
     *
     * @return MIME Type
     */
    public String getMimeType() {
        return Strings.format("{}/{}", type, subtype);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentType that = (ContentType) o;
        return type.equals(that.type) && subtype.equals(that.subtype) && Objects.equals(charset, that.charset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, subtype, charset);
    }

    @Override
    public String toString() {
        if (charset == null) {
            return Strings.format("{}/{}", type, subtype);
        }
        return Strings.format("{}/{}; charset={}", type, subtype, charset.name());
    }

    /**
     * 通过文件后缀名转为MIME
     *
     * @param fileExt 文件后缀名
     * @return {@link ContentType}
     */
    public static ContentType parseByFileExt(String fileExt) {
        if (Strings.isEmpty(fileExt)) {
            return DEFAULT_BINARY;
        }
        fileExt = fileExt.toLowerCase();
        ContentType contentType = SUPPORT_FILE_EXTS.get(fileExt);
        return (contentType == null) ? DEFAULT_BINARY : contentType;
    }

    /**
     * 解析
     *
     * @param contentType 待解析的内容
     * @return {@linkplain  ContentType}
     */
    @NotNull
    public static ContentType parse(String contentType) {
        Asserts.notEmpty(contentType, "contentType is null or empty");

        int index = contentType.indexOf(';');
        String fullType = (index >= 0 ? contentType.substring(0, index) : contentType).trim();
        if (fullType.isEmpty()) {
            throw new IllegalArgumentException("'mediaType' must not be empty");
        }

        // java.net.HttpURLConnection returns a *; q=.2 Accept header
        if ("*".equals(fullType)) {
            fullType = "*/*";
        }
        int subIndex = fullType.indexOf('/');
        if (subIndex == -1) {
            throw new IllegalArgumentException("contentType " + contentType + " does not contain '/'");
        }
        if (subIndex == fullType.length() - 1) {
            throw new IllegalArgumentException("contentType " + contentType + " does not contain subtype after '/'");
        }
        String type = fullType.substring(0, subIndex);
        String subtype = fullType.substring(subIndex + 1);
        if ("*".equals(type) && !"*".equals(subtype)) {
            throw new IllegalArgumentException("contentType " + contentType + " wildcard type is legal only in '*/*' (all mime types)");
        }

        Map<String, String> parameters = null;
        do {
            int nextIndex = index + 1;
            boolean quoted = false;
            while (nextIndex < contentType.length()) {
                char ch = contentType.charAt(nextIndex);
                if (ch == ';') {
                    if (!quoted) {
                        break;
                    }
                } else if (ch == '"') {
                    quoted = !quoted;
                }
                nextIndex++;
            }
            String parameter = contentType.substring(index + 1, nextIndex).trim();
            if (parameter.length() > 0) {
                if (parameters == null) {
                    parameters = new LinkedHashMap<>(4);
                }
                int eqIndex = parameter.indexOf('=');
                if (eqIndex >= 0) {
                    String attribute = parameter.substring(0, eqIndex).trim();
                    String value = parameter.substring(eqIndex + 1).trim();
                    parameters.put(attribute, value);
                }
            }
            index = nextIndex;
        } while (index < contentType.length());

        try {
            Charset charset = null;
            if (parameters != null) {
                charset = Charsets.forName(parameters.get(PARAM_CHARSET), null);
            }
            return new ContentType(type, subtype, charset);
        } catch (UnsupportedCharsetException ex) {
            throw new IllegalArgumentException("contentType " + contentType + " unsupported charset '" + ex.getCharsetName() + "'");
        }
    }

}
