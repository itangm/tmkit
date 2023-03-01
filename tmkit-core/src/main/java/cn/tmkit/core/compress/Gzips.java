package cn.tmkit.core.compress;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.lang.Asserts;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * GZIP工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class Gzips {

    /**
     * 测试流是否是Gzip
     * 流不会关闭并且会重置游标
     *
     * @param in 输入流
     * @return 如果是{@code gzipped则返回{@code true},否则返回{@code false}
     */
    public static boolean isGzipStream(InputStream in) {
        Asserts.notNull(in);
        try {
            in.mark(2);
            int b = in.read();
            int magic = in.read() << 8 | b;
            in.reset();
            return magic == GZIPInputStream.GZIP_MAGIC;
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

}
