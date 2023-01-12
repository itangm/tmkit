package cn.tmkit.core.io;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.lang.Charsets;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * 更快的{@code ByteArrayOutputStream}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class FastByteArrayOutputStream extends OutputStream {

    private final FastByteBuffer fastByteBuffer;

    public FastByteArrayOutputStream() {
        this(1024);
    }

    public FastByteArrayOutputStream(int bufferSize) {
        this.fastByteBuffer = new FastByteBuffer(bufferSize);
    }

    /**
     * Writes <code>b.length</code> bytes from the specified byte array
     * to this output stream. The general contract for <code>write(b)</code>
     * is that it should have exactly the same effect as the call
     * <code>write(b, 0, b.length)</code>.
     *
     * @param b the data.
     * @see OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(byte @NotNull [] b) {
        fastByteBuffer.append(b);
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this output stream.
     * The general contract for <code>write(b, off, len)</code> is that
     * some of the bytes in the array <code>b</code> are written to the
     * output stream in order; element <code>b[off]</code> is the first
     * byte written and <code>b[off+len-1]</code> is the last byte written
     * by this operation.
     * <p>
     * The <code>write</code> method of <code>OutputStream</code> calls
     * the write method of one argument on each of the bytes to be
     * written out. Subclasses are encouraged to override this method and
     * provide a more efficient implementation.
     * <p>
     * If <code>b</code> is <code>null</code>, a
     * <code>NullPointerException</code> is thrown.
     * <p>
     * If <code>off</code> is negative, or <code>len</code> is negative, or
     * <code>off+len</code> is greater than the length of the array
     * <code>b</code>, then an <tt>IndexOutOfBoundsException</tt> is thrown.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     */
    @Override
    public void write(byte @NotNull [] b, int off, int len) {
        fastByteBuffer.append(b, off, len);
    }

    /**
     * Writes the specified byte to this output stream. The general
     * contract for <code>write</code> is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument <code>b</code>. The 24
     * high-order bits of <code>b</code> are ignored.
     * <p>
     * Subclasses of <code>OutputStream</code> must provide an
     * implementation for this method.
     *
     * @param b the <code>byte</code>.
     */
    @Override
    public void write(int b) {
        fastByteBuffer.append((byte) b);
    }

    /**
     * 返回数据大小
     *
     * @return 数据大小
     */
    public int size() {
        return fastByteBuffer.size();
    }

    /**
     * 此方法无任何效果，当流被关闭后不会抛出IOException
     */
    @Override
    public void close() {
        //ignore
    }

    /**
     * 重置缓冲区内容
     */
    public void reset() {
        fastByteBuffer.reset();
    }

    public void writeTo(OutputStream out) {
        final int index = fastByteBuffer.index();
        byte[] buffer;
        try {
            for (int i = 0; i < index; i++) {
                buffer = fastByteBuffer.array(i);
                out.write(buffer);
            }
            out.write(fastByteBuffer.array(index), 0, fastByteBuffer.offset());
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 转为Byte数组
     *
     * @return Byte数组
     */
    public byte[] toByteArray() {
        return fastByteBuffer.toArray();
    }

    @Override
    public String toString() {
        return new String(toByteArray());
    }

    /**
     * 转为字符串
     *
     * @param charsetName 编码
     * @return 字符串
     */
    public String toString(String charsetName) {
        return toString(Charsets.forName(charsetName));
    }

    /**
     * 转为字符串
     *
     * @param charset 编码
     * @return 字符串
     */
    public String toString(Charset charset) {
        return new String(toByteArray(), charset);
    }

}
