package cn.tmkit.web.servlet3.request;

import cn.tmkit.core.exception.GenericRuntimeException;
import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.http.shf4j.HttpMethod;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * 比较完善的Request包装类：支持输入流重复读取，支持getParameter等方法调用
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class RepeatableHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] content;
    private final boolean isMultipart;
    private Collection<Part> parts;

    public RepeatableHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            String contentType = request.getContentType();
            isMultipart = HttpMethod.POST.match(request.getMethod()) &&
                    Strings.startsWithIgnoreCase(contentType, "multipart/form-data");
            if (isMultipart) {
                parts = request.getParts();
            }
            this.content = IoUtil.readBytes(request.getInputStream());
        } catch (Exception e) {
            throw new GenericRuntimeException(e);
        }
    }

    @Override
    public Collection<Part> getParts() {
        return parts;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return parts.stream().filter(item -> item.getName().equals(name))
                .findAny().orElse(null);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return IoUtil.getBufferedReader(getInputStream(), getCharacterEncoding());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isMultipart) {
            return super.getInputStream();
        }
        final ByteArrayInputStream byteArray = new ByteArrayInputStream(content);
        return new ServletInputStream() {

            @Override
            public synchronized void mark(int readLimit) {
                byteArray.mark(readLimit);
            }

            @Override
            public synchronized void reset() {
                byteArray.reset();
            }

            @Override
            public boolean isFinished() {
                return byteArray.available() <= 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() throws IOException {
                return byteArray.read();
            }
        };
    }

    /**
     * 判断该请求是否为{@code multipart/form-data}
     *
     * @return 如果是{@code multipart/form-data}则为{@code true}
     */
    public boolean isMultipart() {
        return isMultipart;
    }

}
