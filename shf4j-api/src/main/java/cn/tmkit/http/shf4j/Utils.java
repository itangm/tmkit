package cn.tmkit.http.shf4j;

import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.Collections;

import java.io.InputStream;
import java.util.List;

/**
 * 工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class Utils {

    public static void closeParts(RequestBody requestBody) {
        if (requestBody instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) requestBody;
            List<MultipartBody.Part> parts = multipartBody.getParts();
            if (Collections.isNotEmpty(parts)) {
                parts.forEach(part -> {
                    InputStream in = part.getIn();
                    if (in != null) {
                        IoUtil.closeQuietly(in);
                    }
                });
            }
        }
    }

}
