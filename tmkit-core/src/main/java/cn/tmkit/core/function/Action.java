package cn.tmkit.core.function;

/**
 * 执行动作的函数式接口
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-10-21
 */
@FunctionalInterface
public interface Action {

    /**
     * 执行的动作
     */
    void execute();

}
