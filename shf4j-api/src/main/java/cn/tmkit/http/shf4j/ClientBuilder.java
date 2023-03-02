package cn.tmkit.http.shf4j;

import cn.tmkit.core.support.CloneSupport;
import org.jetbrains.annotations.Nullable;

/**
 * Client构造器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public abstract class ClientBuilder extends CloneSupport<ClientBuilder> {

    public abstract Client build(@Nullable Options options);

    static class Default extends ClientBuilder {

        @Override
        public Client build(Options options) {
            return new Client.DefaultClient();
        }

    }

}
