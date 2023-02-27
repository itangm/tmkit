package cn.tmkit.core.id.snowflake;

import cn.tmkit.core.exception.IdGeneratorException;

/**
 * 优化的雪花算法（雪花漂移），性能一般，但是长度较短，前端不会出现JavaScript Number溢出的问题
 * <p>
 * 生成的ID更短，速度更快，算法来源于https://github.com/yitter/IdGenerator
 * 这里仅仅做了一些编码修改，符合Java风格
 * </p>
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class FastSnowflake {

    /**
     * 基础时间(基点时间、原点时间、纪元时间)
     */
    protected final long baseTime;

    /**
     * 机器码
     * 必须 全局唯一（或相同 DataCenterId 内唯一）
     */
    protected final short workerId;

    /**
     * 机器码位长
     * 决定 WorkerId 的最大值，默认值6，取值范围 [1, 19]
     */
    protected final byte workerIdBitLength;

    /**
     * 自增序列数位长
     * 默认值6，取值范围 [3, 21]（建议不小于4），决定每毫秒基础生成的ID个数。规则要求：WorkerIdBitLength + SeqBitLength 不超过 22。
     */
    protected final byte seqBitLength;

    /**
     * 最大序列数（含）
     * 设置范围 [MinSeqNumber, 2^SeqBitLength-1]，默认值0，真实最大序列数取最大值（2^SeqBitLength-1），不为0时，取其为真实最大序列数，
     * 一般无需设置，除非多机共享WorkerId分段生成ID（此时还要正确设置最小序列数）。
     */
    protected final int maxSeqNumber;

    /**
     * 最小序列数（含）
     * 默认值5，取值范围 [5, MaxSeqNumber]，每毫秒的前5个序列数对应编号0-4是保留位，其中1-4是时间回拨相应预留位，0是手工新值预留位。
     */
    protected final short minSeqNumber;

    /**
     * 最大漂移次数（含）
     */
    protected final int topOverCostCount;


    protected final byte timestampShift;

    /**
     * 对象锁
     */
    protected final static Object SYNC_LOCK = new Object();

    protected short currentSeqNumber;
    protected long lastTimeTick = 0;
    protected long turnBackTimeTick = 0;
    protected byte turnBackIndex = 0;

    protected boolean isOverCost = false;
    protected int overCostCountInOneTerm = 0;
    protected int genCountInOneTerm = 0;
    protected int termIndex = 0;

    public FastSnowflake() {
        this(SnowFlakeOptions.builder().build());
    }

    public FastSnowflake(SnowFlakeOptions options) {
        this.baseTime = options.getBaseTime();
        this.workerIdBitLength = options.getWorkerIdBitLength();
        this.workerId = options.getWorkerId();
        this.seqBitLength = options.getSeqBitLength();
        this.maxSeqNumber = options.getMaxSeqNumber() == 0 ? (1 << this.seqBitLength) : options.getMaxSeqNumber();
        this.minSeqNumber = options.getMinSeqNumber();
        this.topOverCostCount = options.getTopOverCostCount();
        this.timestampShift = (byte) (this.workerIdBitLength + this.seqBitLength);
        this.currentSeqNumber = this.minSeqNumber;
    }

    private void endOverCostAction(long useTimeTick) {
        if (this.termIndex > 10000) {
            this.termIndex = 0;
        }
    }

    /**
     * 获取当前时间戳差值
     *
     * @return 时间戳差值
     */
    protected long getCurrentTimeTick() {
        long millis = System.currentTimeMillis();
        return millis - this.baseTime;
    }

    private long nextOverCostId() {
        long currentTimeTick = getCurrentTimeTick();

        if (currentTimeTick > this.lastTimeTick) {
            this.endOverCostAction(currentTimeTick);

            this.lastTimeTick = currentTimeTick;
            this.currentSeqNumber = this.minSeqNumber;
            this.isOverCost = false;
            this.overCostCountInOneTerm = 0;
            this.genCountInOneTerm = 0;

            return this.calcId(this.lastTimeTick);
        }

        if (this.overCostCountInOneTerm >= this.topOverCostCount) {
            this.endOverCostAction(currentTimeTick);

            this.lastTimeTick = this.getNextTimeTick();
            this.currentSeqNumber = this.minSeqNumber;
            this.isOverCost = false;
            this.overCostCountInOneTerm = 0;
            this.genCountInOneTerm = 0;

            return this.calcId(this.lastTimeTick);
        }

        if (this.currentSeqNumber > this.maxSeqNumber) {
            this.lastTimeTick++;
            this.currentSeqNumber = this.minSeqNumber;
            this.isOverCost = true;
            this.overCostCountInOneTerm++;
            this.genCountInOneTerm++;

            return this.calcId(this.lastTimeTick);
        }

        this.genCountInOneTerm++;
        return this.calcId(this.lastTimeTick);
    }


    private long nextNormalId() throws IdGeneratorException {
        long currentTimeTick = this.getCurrentTimeTick();

        if (currentTimeTick < this.lastTimeTick) {
            if (this.turnBackTimeTick < 1) {
                this.turnBackTimeTick = this.lastTimeTick - 1;
                this.turnBackIndex++;

                // 每毫秒序列数的前5位是预留位，0用于手工新值，1-4是时间回拨次序
                // 支持4次回拨次序（避免回拨重叠导致ID重复），可无限次回拨（次序循环使用）。
                if (this.turnBackIndex > 4) {
                    this.turnBackIndex = 1;
                }
                // this.beginTurnBackAction(this.turnBackTimeTick);
            }

//            try {
//                 Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            return this.calcTurnBackId(this.turnBackTimeTick);
        }

        // 时间追平时，this.turnBackTimeTick清零
        if (this.turnBackTimeTick > 0) {
            // this.endTurnBackAction(this.turnBackTimeTick);
            this.turnBackTimeTick = 0;
        }

        if (currentTimeTick > this.lastTimeTick) {
            this.lastTimeTick = currentTimeTick;
            this.currentSeqNumber = this.minSeqNumber;

            return this.calcId(this.lastTimeTick);
        }

        if (this.currentSeqNumber > this.maxSeqNumber) {
            // this.beginOverCostAction(currentTimeTick);

            this.termIndex++;
            this.lastTimeTick++;
            this.currentSeqNumber = this.minSeqNumber;
            this.isOverCost = true;
            this.overCostCountInOneTerm = 1;
            this.genCountInOneTerm = 1;

            return this.calcId(this.lastTimeTick);
        }

        return this.calcId(this.lastTimeTick);
    }

    private long calcId(long useTimeTick) {
        long result = ((useTimeTick << this.timestampShift) +
                ((long) this.workerId << this.seqBitLength) + this.currentSeqNumber);

        this.currentSeqNumber++;
        return result;
    }

    private long calcTurnBackId(long useTimeTick) {
        long result = ((useTimeTick << this.timestampShift) +
                ((long) this.workerId << this.seqBitLength) + this.turnBackIndex);

        this.turnBackTimeTick--;
        return result;
    }

    protected long getNextTimeTick() {
        long tempTimeTicker = this.getCurrentTimeTick();

        while (tempTimeTicker <= this.lastTimeTick) {
            tempTimeTicker = this.getCurrentTimeTick();
        }

        return tempTimeTicker;
    }

    /**
     * 生成ID
     *
     * @return id
     */
    public long nextId() {
        synchronized (SYNC_LOCK) {
            return this.isOverCost ? this.nextOverCostId() : this.nextNormalId();
        }
    }

}
