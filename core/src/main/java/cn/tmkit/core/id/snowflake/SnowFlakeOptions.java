package cn.tmkit.core.id.snowflake;

import cn.tmkit.core.lang.Builder;
import lombok.Getter;

/**
 * 雪花算法的配置项
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
@Getter
public class SnowFlakeOptions {

    /**
     * 雪花算法的运算规则
     */
    private final Rule rule;

    /**
     * 基础时间（ms单位）
     * 不能超过当前系统时间，默认值为 2022-03-01 00:00:00
     */
    private final long baseTime;

    /**
     * 机器码
     * 必须由外部设定，最大值 2^WorkerIdBitLength-1
     */
    private final short workerId;

    /**
     * 机器码位长
     * 默认值6，取值范围 [1, 15]（要求：序列数位长+机器码位长不超过22）
     */
    private final byte workerIdBitLength;

    /**
     * 序列数位长
     * 默认值6，取值范围 [3, 21]（要求：序列数位长+机器码位长不超过22）
     */
    private final byte seqBitLength;

    /**
     * 最大序列数（含）
     * 设置范围 [MinSeqNumber, 2^SeqBitLength-1]，默认值0，表示最大序列数取最大值（2^SeqBitLength-1]）
     */
    private final short maxSeqNumber;

    /**
     * 最小序列数（含）
     * 默认值5，取值范围 [5, MaxSeqNumber]，每毫秒的前5个序列数对应编号是0-4是保留位，其中1-4是时间回拨相应预留位，0是手工新值预留位
     */
    public final short minSeqNumber;

    /**
     * 最大漂移次数（含）
     * 默认2000，推荐范围500-10000（与计算能力有关）
     */
    public final short topOverCostCount;

    public SnowFlakeOptions(SnowFlakeOptionsBuilder builder) {
        this.rule = builder.rule;
        this.baseTime = builder.baseTime;
        this.workerIdBitLength = builder.workerIdBitLength;
        this.workerId = builder.workerId;
        this.seqBitLength = builder.seqBitLength;
        this.maxSeqNumber = builder.maxSeqNumber;
        this.minSeqNumber = builder.minSeqNumber;
        this.topOverCostCount = builder.topOverCostCount;
    }

    /**
     * 雪花算法的运算规则
     */
    public enum Rule {

        /**
         * 快速
         */
        FAST,

        /**
         * 传统的
         */
        TRAD,


    }

    /**
     * {@linkplain SnowFlakeOptions}的构建
     */
    public static class SnowFlakeOptionsBuilder implements Builder<SnowFlakeOptions> {

        /**
         * 雪花算法的运算规则
         */
        private Rule rule = Rule.FAST;

        /**
         * 基础时间（ms单位）
         */
        private long baseTime = 1677600000000L;

        /**
         * 机器码
         */
        private short workerId = 0;

        /**
         * 机器码位长
         */
        private byte workerIdBitLength = 6;

        /**
         * 序列数位长
         */
        private byte seqBitLength = 6;

        /**
         * 最大序列数（含）
         * 设置范围 [MinSeqNumber, 2^SeqBitLength-1]，默认值0，表示最大序列数取最大值（2^SeqBitLength-1]）
         */
        private short maxSeqNumber = 0;

        /**
         * 最小序列数（含）
         * 默认值5，取值范围 [5, MaxSeqNumber]，每毫秒的前5个序列数对应编号是0-4是保留位，其中1-4是时间回拨相应预留位，0是手工新值预留位
         */
        private short minSeqNumber = 5;

        /**
         * 最大漂移次数（含）
         * 默认2000，推荐范围500-10000（与计算能力有关）
         */
        private short topOverCostCount = 2000;

        /**
         * 雪花算法的运算规则
         *
         * @param rule 运算规则
         */
        public SnowFlakeOptionsBuilder rule(Rule rule) {
            if (rule != null) {
                this.rule = rule;
            }
            return this;
        }

        /**
         * 雪花算法的运算规则，{@code baseTime}不大于0不生效
         * 不能超过当前系统时间，默认值为 2022-06-11 12:00:00
         *
         * @param baseTime 基础时间（ms单位）
         */
        public SnowFlakeOptionsBuilder baseTime(long baseTime) {
            if (baseTime > 0) {
                this.baseTime = baseTime;
            }
            return this;
        }

        /**
         * 必须由外部设定，最大值 2^WorkerIdBitLength-1
         *
         * @param workerId 机器码
         */
        public SnowFlakeOptionsBuilder workerId(short workerId) {
            if (workerId >= 0) {
                this.workerId = workerId;
            }
            return this;
        }

        /**
         * 机器码位长
         * 默认值6，取值范围 [1, 15]（要求：序列数位长+机器码位长不超过22）
         *
         * @param workerIdBitLength 机器码位长
         */
        public SnowFlakeOptionsBuilder workerIdBitLength(int workerIdBitLength) {
            if (workerIdBitLength <= 0 || workerIdBitLength > 15) {
                throw new IllegalArgumentException("Illegal workerIdBitLength,range[1,15]");
            }
            this.workerIdBitLength = (byte) workerIdBitLength;
            return this;
        }

        /**
         * 序列数位长
         * 默认值6，取值范围 [3, 21]（要求：序列数位长+机器码位长不超过22）
         *
         * @param seqBitLength 序列数位长
         */
        public SnowFlakeOptionsBuilder seqBitLength(int seqBitLength) {
            if (seqBitLength < 3 || seqBitLength > 21) {
                throw new IllegalArgumentException("Illegal seqBitLength,range[3,21]");
            }
            this.seqBitLength = (byte) seqBitLength;
            return this;
        }

        /**
         * 设置范围 [MinSeqNumber, 2^SeqBitLength-1]，默认值0，表示最大序列数取最大值（2^SeqBitLength-1]）
         *
         * @param maxSeqNumber 最大序列数（含）
         */
        public SnowFlakeOptionsBuilder maxSeqNumber(short maxSeqNumber) {
            this.maxSeqNumber = maxSeqNumber;
            return this;
        }

        /**
         * 默认值5，取值范围 [5, MaxSeqNumber]，每毫秒的前5个序列数对应编号是0-4是保留位，其中1-4是时间回拨相应预留位，0是手工新值预留位
         *
         * @param minSeqNumber 雪最小序列数（含）
         */
        public SnowFlakeOptionsBuilder minSeqNumber(short minSeqNumber) {
            if (minSeqNumber < 5) {
                throw new IllegalArgumentException("Illegal minSeqNumber,range[5,maxSeqNumber]");
            }
            this.minSeqNumber = minSeqNumber;
            return this;
        }

        /**
         * 默认2000，推荐范围500-10000（与计算能力有关）
         *
         * @param topOverCostCount 最大漂移次数（含）
         */
        public SnowFlakeOptionsBuilder topOverCostCount(short topOverCostCount) {
            if (topOverCostCount >= 500) {
                this.topOverCostCount = topOverCostCount;
            }
            return this;
        }

        /**
         * 构建
         *
         * @return 被构建的对象
         */
        @Override
        public SnowFlakeOptions build() {
            return new SnowFlakeOptions(this);
        }
    }

    /**
     * {@linkplain SnowFlakeOptionsBuilder}
     *
     * @return {@linkplain SnowFlakeOptionsBuilder}
     */
    public static SnowFlakeOptionsBuilder builder() {
        return new SnowFlakeOptionsBuilder();
    }

}
