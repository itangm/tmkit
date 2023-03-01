package cn.tmkit.core.id;

import cn.tmkit.core.id.snowflake.FastSnowflake;
import cn.tmkit.core.lang.reflect.Singletons;

/**
 * witter的Snowflake 算法 分布式系统中，有一些需要使用全局唯一ID的场景，有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。
 * snowflake的结构如下(每部分用-分开):
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * <p>
 * 第一位为未使用(符号位表示正数)，接下来的41位为毫秒级时间(41位的长度可以使用69年) 然后是5位datacenterId和5位workerId(10位的长度最多支持部署1024个节点） 最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生4096个ID序号）
 * 并且可以通过生成的id反推出生成时间,datacenterId和workerId
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class SnowflakeIdGenerator implements IdGenerator<Long> {

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public Long get() {
        return Singletons.get(FastSnowflake.class).nextId();
    }

}
