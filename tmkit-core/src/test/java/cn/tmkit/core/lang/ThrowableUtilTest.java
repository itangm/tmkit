package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

/**
 * Tester for {@linkplain ThrowableUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2024-01-30
 */
public class ThrowableUtilTest {

    @Test
    public void getStacktraceStr() {
        try {
            int r = 1 / 0;
        } catch (Exception e) {
            String stacktraceStr = ThrowableUtil.getStacktraceStr(e);
            System.err.println(stacktraceStr);
        }
    }

    @Test
    public void getStacktraceStrToOneLine() {
        try {
            throw new Error("Test Ex");
        } catch (Throwable e) {
            String stacktraceStr = ThrowableUtil.getStacktraceStrToOneLine(e);
            System.err.println(stacktraceStr);
        }
    }

}
