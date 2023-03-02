package cn.tmkit.web.servlet3.response;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.io.Files;
import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.core.lang.regex.Regexes;
import cn.tmkit.http.shf4j.ContentType;
import cn.tmkit.json.sjf4j.util.JSONs;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;

/**
 * HTTP Response工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class Responses {

    private static final String BROWSER_IE = "MSIE";
    private static final String BROWSER_EDGE = "Edge";
    private static final String BROWSER_CHROME = "Chrome";
    private static final String BROWSER_SAFARI = "Safari";
    private static final String BROWSER_FIREFOX = "Firefox";

    /**
     * 向{@linkplain HttpServletResponse}写入JSON,默认采用{@code UTF-8}
     *
     * @param obj      对象
     * @param response HTTP响应对象
     */
    public static void writeJson(@Nullable Object obj, @Nullable HttpServletResponse response) {
        writeJson(JSONs.toJson(obj), response);
    }

    /**
     * 向{@linkplain HttpServletResponse}写入JSON,默认采用{@code UTF-8}
     *
     * @param json     json字符串
     * @param response HTTP响应对象
     */
    public static void writeJson(@Nullable String json, @Nullable HttpServletResponse response) {
        writeJson(json, null, response);
    }

    public static void writeJson(@Nullable Object obj, @Nullable Charset encoding, @Nullable HttpServletResponse response) {
        writeJson(JSONs.toJson(obj), encoding, response);
    }

    /**
     * 向{@linkplain HttpServletResponse}写入JSON,采用指定编码
     *
     * @param json     json字符串
     * @param encoding 指定编码,如果为空则使用{@code UTF-8}
     * @param response HTTP响应对象
     */
    public static void writeJson(@Nullable String json, @Nullable Charset encoding, @Nullable HttpServletResponse response) {
        if (Objects.isAnyNull(json, response)) {
            return;
        }
        encoding = Charsets.getCharset(encoding, Charsets.UTF_8);
        write(json, ContentType.APPLICATION_JSON.charset(encoding), response);
    }

    private static void write(String data, ContentType contentType, HttpServletResponse response) {
        response.setCharacterEncoding(contentType.getCharset().name());
        response.setContentType(contentType.toString());
        try {
            response.getWriter().write(data);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }


    /**
     * 对浏览器输出HTML片段
     *
     * @param html     待输出的内容
     * @param response {@linkplain HttpServletResponse}
     */
    public static void writeHtml(@Nullable String html, @Nullable HttpServletResponse response) {
        writeHtml(html, response, null);
    }

    /**
     * 对浏览器输出HTML片段
     *
     * @param html     待输出的内容
     * @param response {@linkplain HttpServletResponse}
     * @param encoding 字符集编码
     */
    public static void writeHtml(@Nullable String html, @Nullable HttpServletResponse response, @Nullable Charset encoding) {
        if (Objects.isAnyNull(html, response)) {
            return;
        }
        encoding = Charsets.getCharset(encoding, Charsets.UTF_8);
        write(html, ContentType.TEXT_HTML.charset(encoding), response);
    }


    /**
     * 对浏览器输出文本内容
     *
     * @param data     待输出的字符串
     * @param response {@linkplain HttpServletResponse}
     */
    public static void writePlainText(@Nullable String data, @Nullable HttpServletResponse response) {
        writePlainText(data, response, null);
    }

    /**
     * 对浏览器输出文本内容
     *
     * @param data     待输出的字符串
     * @param response {@linkplain HttpServletResponse}
     * @param encoding 编码
     */
    public static void writePlainText(@Nullable String data, @Nullable HttpServletResponse response, @Nullable Charset encoding) {
        if (Objects.isAnyNull(data, response)) {
            return;
        }
        encoding = Charsets.getCharset(encoding, Charsets.UTF_8);
        write(data, ContentType.TEXT_PLAIN.charset(encoding), response);
    }

    /**
     * 对浏览器输出文本内容
     *
     * @param data     待输出的对象，最终会转为JSON字符串
     * @param response {@linkplain HttpServletResponse}
     */
    public static void writePlainText(@Nullable Object data, @Nullable HttpServletResponse response) {
        writePlainText(JSONs.toJson(data), response);
    }

    /**
     * 对浏览器输出文件，原文件不会被删除。
     *
     * @param request      HTTP请求对象
     * @param response     HTTP响应对象
     * @param downloadFile 下载文件对象
     */
    public static void writeFile(@Nullable File downloadFile, @Nullable HttpServletRequest request, @Nullable HttpServletResponse response) {
        writeFile(downloadFile, false, request, response);
    }

    /**
     * 对浏览器输出文件
     *
     * @param downloadFile 要下载的文件
     * @param isDeleted    是否需要删除当前文件
     * @param request      HTTP请求对象
     * @param response     HTTP响应对象
     */
    public static void writeFile(File downloadFile, boolean isDeleted, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.isNull(downloadFile)) {
            return;
        }
        writeFile(downloadFile.getName(), downloadFile, isDeleted, request, response);
    }

    /**
     * 对浏览器输出文件
     *
     * @param displayName  对外显示的下载文件名
     * @param downloadFile 要下载的文件
     * @param isDeleted    是否需要删除当前文件
     * @param request      HTTP请求对象
     * @param response     HTTP响应对象
     */
    public static void writeFile(String displayName, File downloadFile, boolean isDeleted, HttpServletRequest request, HttpServletResponse response) {
        String fileExt = Files.getFileExt(displayName);
        if (fileExt == null) {
            fileExt = Files.getFileExt(downloadFile);
        }
        ContentType contentType = ContentType.parseByFileExt(fileExt);
        try (FileInputStream in = Files.openFileInputStream(downloadFile)) {
            writeFile(displayName, in, downloadFile.length(), contentType, request, response);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
        if (isDeleted) {
            Files.delete(downloadFile);
        }
    }

    /**
     * 对浏览器输出文件
     *
     * @param displayName   对外显示的下载文件名
     * @param in            输入流，要下载的内容
     * @param contentLength 内容长度
     * @param contentType   内容类型
     * @param request       HTTP请求对象
     * @param response      HTTP响应对象
     */
    public static void writeFile(String displayName, InputStream in, long contentLength, ContentType contentType,
                                 HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(contentType.toString());
        if (contentLength != -1) {
            response.setHeader("Content-Length", String.valueOf(contentLength));
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + getBrowserDownloadFilename(request, displayName));
        try (OutputStream out = response.getOutputStream()) {
            if (IoUtil.copy(in, out) == -1) {
                throw new IOException("Copy input stream to output stream failed.");
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 转义文件下载名
     *
     * @param request          HTTP请求对象
     * @param downloadFilename 原下载文件名
     * @return 返回转义后的下载名
     */
    public static String getBrowserDownloadFilename(HttpServletRequest request, String downloadFilename) {
        String browser = getBrowser(request);
        if (BROWSER_IE.equals(browser) || BROWSER_EDGE.equals(browser)) {
            //IE浏览器，采用URLEncoder编码
            String filename = toUtf8String(downloadFilename);
            // see http://support.microsoft.com/default.aspx?kbid=816868
            if (filename.length() > 150) {
                // 根据request的locale 得出可能的编码
                filename = new String(filename.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
            }
            return filename;
        }
        if (BROWSER_SAFARI.equals(browser)) {
            //Safari浏览器，采用ISO编码的中文输出
            return new String(downloadFilename.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
        }
        //Chrome浏览器，可以采用MimeUtility编码或ISO编码的中文输出
        //FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
        //MimeUtility.encodeText(downloadFilename,)
        return new String(downloadFilename.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
    }

    /**
     * 转成UTF8格式的字符串
     */
    private static String toUtf8String(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes(Charsets.UTF_8);
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (byte aB : b) {
                    int k = aB;
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%").append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取浏览器类型
     *
     * @param request HTTP请求对象
     * @return 浏览器类型
     * @see #BROWSER_IE
     * @see #BROWSER_EDGE
     * @see #BROWSER_CHROME
     * @see #BROWSER_SAFARI
     * @see #BROWSER_FIREFOX
     */
    private static String getBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (Strings.isEmpty(userAgent)) {
            return BROWSER_CHROME;
        }
        /*首先判断是否是IE浏览器*/
        if (Regexes.isMatch("MISE", userAgent)) {
            return BROWSER_IE;
        } else if (Regexes.isMatch("Trident", userAgent)) {
            return BROWSER_IE;
        } else if (Regexes.isMatch("Edge", userAgent)) {
            return BROWSER_EDGE;
        } else if (Regexes.isMatch("Chrome", userAgent)) {
            return BROWSER_CHROME;
        } else if (Regexes.isMatch("Safari", userAgent)) {
            return BROWSER_SAFARI;
        } else if (Regexes.isMatch("Firefox", userAgent)) {
            return BROWSER_FIREFOX;
        }
        return BROWSER_CHROME;

//        Pattern pattern = Pattern.compile("MISE", Pattern.CASE_INSENSITIVE);
//        if (pattern.matcher(userAgent).find()) return BROWSER_IE;
//
//        pattern = Pattern.compile("Trident", Pattern.CASE_INSENSITIVE);
//        if (pattern.matcher(userAgent).find()) return BROWSER_IE;
//
//        pattern = Pattern.compile("Edge", Pattern.CASE_INSENSITIVE);
//        if (pattern.matcher(userAgent).find()) return BROWSER_EDGE;
//
//        pattern = Pattern.compile("Chrome", Pattern.CASE_INSENSITIVE);
//        if (pattern.matcher(userAgent).find()) return BROWSER_CHROME;
//
//        pattern = Pattern.compile("Safari", Pattern.CASE_INSENSITIVE);
//        if (pattern.matcher(userAgent).find()) return BROWSER_SAFARI;
//
//        pattern = Pattern.compile("Firefox", Pattern.CASE_INSENSITIVE);
//        if (pattern.matcher(userAgent).find()) return BROWSER_FIREFOX;
//
//        return BROWSER_CHROME;
    }


}
