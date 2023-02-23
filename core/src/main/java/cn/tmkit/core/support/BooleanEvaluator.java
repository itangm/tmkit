package cn.tmkit.core.support;

import cn.tmkit.core.lang.Collections;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 布尔解析器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public final class BooleanEvaluator {

    private final Set<Object> trueFactors = new HashSet<>();

    private final Set<Object> falseFactors = new HashSet<>();

    /**
     * 当比对的对象为空时返回{@code false}
     */
    private boolean nullValue = false;

    /**
     * 比对的字符串是否忽略大小写
     */
    private boolean strIgnoreCase = true;

    /**
     * 构造器
     *
     * @param nullValue     {@code true} or {@code false}
     * @param strIgnoreCase 字符串大小写
     * @param trueFactors   为真的银子
     * @param falseFactors  为假的银子
     */
    public BooleanEvaluator(boolean nullValue, boolean strIgnoreCase, Object[] trueFactors, Object[] falseFactors) {
        this(nullValue, strIgnoreCase, Collections.arrayList(trueFactors), Collections.arrayList(falseFactors));
    }

    /**
     * 构造器
     *
     * @param nullValue     {@code true} or {@code false}
     * @param strIgnoreCase 字符串大小写
     * @param trueFactors   为真的银子
     * @param falseFactors  为假的银子
     */
    public BooleanEvaluator(boolean nullValue, boolean strIgnoreCase, List<Object> trueFactors, List<Object> falseFactors) {
        setNullValue(nullValue);
        setStrIgnoreCase(strIgnoreCase);
        addTrueFactors(trueFactors);
        addFalseFactors(falseFactors);
    }

    /**
     * 获得空值返回的{@code true}还是{@code false}
     *
     * @return {@code true} or {@code false}
     */
    public boolean isNullValue() {
        return nullValue;
    }

    /**
     * 设置空值返回{@code true}还是{@code false}
     *
     * @param nullValue {@code true} or {@code false}
     */
    public void setNullValue(boolean nullValue) {
        this.nullValue = nullValue;
    }

    /**
     * 获取字符大小写是否忽略
     *
     * @return {@code true} or {@code false}
     */
    public boolean isStrIgnoreCase() {
        return strIgnoreCase;
    }

    /**
     * 设置是否忽略字符串大小写
     *
     * @param strIgnoreCase 字符串大小写
     */
    public void setStrIgnoreCase(boolean strIgnoreCase) {
        this.strIgnoreCase = strIgnoreCase;
    }

    /**
     * 添加为真的因子
     *
     * @param trueFactors 为真的银子
     */
    public void addTrueFactors(List<Object> trueFactors) {
        if (Collections.isNotEmpty(trueFactors)) {
            for (Object object : trueFactors) {
                addTrueFactor(object);
            }
        }
    }

    /**
     * 添加为假的因子
     *
     * @param falseFactors 为假的因子
     */
    public void addFalseFactors(List<Object> falseFactors) {
        if (Collections.isNotEmpty(falseFactors)) {
            for (Object object : falseFactors) {
                addFalseFactor(object);
            }
        }
    }

    /**
     * 添加为真的因子
     *
     * @param trueFactor 为真的银子
     */
    public void addTrueFactor(Object trueFactor) {
        if (trueFactor != null) {
            if (trueFactor instanceof String) {
                trueFactors.add(strIgnoreCase ? ((String) trueFactor).toLowerCase() : trueFactor);
            }
            trueFactors.add(trueFactor);
        }
    }

    /**
     * 添加为假的因子
     *
     * @param falseFactor 为假的因子
     */
    public void addFalseFactor(Object falseFactor) {
        if (falseFactor != null) {
            if (falseFactor instanceof String) {
                falseFactors.add(strIgnoreCase ? ((String) falseFactor).toLowerCase() : falseFactor);
                return;
            }
            falseFactors.add(falseFactor);
        }
    }

    /**
     * 添加因子
     *
     * @param trueFactor  为真的银子
     * @param falseFactor 为假的因子
     */
    public void addFactor(Object trueFactor, Object falseFactor) {
        addTrueFactor(trueFactor);
        addFalseFactor(falseFactor);
    }

    /**
     * 判断对象是否为{@code true}，如果是{@code true}则返回{@code true}
     *
     * @param object 待处理的对象
     * @return 如果{@code object}是{@code true}则返回{@code true}，否则返回{@code false}
     */
    public boolean evalTrue(Object object) {
        if (object == null) {
            return nullValue;
        }
        if (object instanceof String && strIgnoreCase) {
            object = ((String) object).toLowerCase();
        }
        return trueFactors.contains(object);
    }

    /**
     * 判断对象是否为{@code false}，如果是{@code false}则返回{@code true}
     *
     * @param object 待处理的对象
     * @return 如果{@code object}是{@code false}则返回{@code true}，否则返回{@code false}
     */
    public boolean evalFalse(Object object) {
        if (object == null) {
            return nullValue;
        }
        if (object instanceof String && strIgnoreCase) {
            object = ((String) object).toLowerCase();
        }
        return falseFactors.contains(object);
    }

    /**
     * 创建自定义的{@code False}评估类
     *
     * @param nullValue        对比项为空返回值
     * @param stringIgnoreCase 字符串项是否忽略大小写
     * @param falseArray       为{@code false}的相关数组
     * @return {@linkplain BooleanEvaluator}
     */
    public static BooleanEvaluator createFalseEvaluator(boolean nullValue, boolean stringIgnoreCase, Object... falseArray) {
        return new BooleanEvaluator(nullValue, stringIgnoreCase, null, falseArray);
    }

    /**
     * 创建自定义的{@code True}评估类
     *
     * @param nullValue        对比项为空返回值
     * @param stringIgnoreCase 字符串项是否忽略大小写
     * @param truthArray       为{@code true}的相关数组
     * @return {@linkplain BooleanEvaluator}
     */
    public static BooleanEvaluator createTrueEvaluator(boolean nullValue, boolean stringIgnoreCase, Object... truthArray) {
        return new BooleanEvaluator(nullValue, stringIgnoreCase, truthArray, null);
    }

    /**
     * 默认的True解析器
     */
    public static final BooleanEvaluator DEFAULT_TRUE_EVALUATOR = BooleanEvaluator.createTrueEvaluator(false, true,
            true, "true", 1, "1", "yes", "y", "no", "n", "t", "ok", "是", "对", "真", "對", "√");

}
