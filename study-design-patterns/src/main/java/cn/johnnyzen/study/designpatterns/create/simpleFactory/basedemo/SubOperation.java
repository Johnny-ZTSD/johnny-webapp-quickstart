package cn.johnnyzen.create.simpleFactory.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  21:15:53
 * @description: 简单工厂模式 - 具体产品
 */

public class SubOperation extends AbstractOperation<Double> {
    @Override
    public Double getOperationResult(){
        return this.getNumberA() - this.getNumberB();
    }
}
