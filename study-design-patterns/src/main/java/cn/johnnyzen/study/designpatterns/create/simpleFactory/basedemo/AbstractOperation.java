package cn.johnnyzen.create.simpleFactory.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  20:12:51
 * @description: 简单工厂模式 - 抽象产品
 */

public abstract class AbstractOperation<T> {
    private T numberA;

    private T numberB;

    /**
     * 获取运算结果
     */
    public T getOperationResult(){
        return null;
    }

    public T getNumberA() {
        return numberA;
    }

    public void setNumberA(T numberA) {
        this.numberA = numberA;
    }

    public T getNumberB() {
        return numberB;
    }

    public void setNumberB(T numberB) {
        this.numberB = numberB;
    }
}
