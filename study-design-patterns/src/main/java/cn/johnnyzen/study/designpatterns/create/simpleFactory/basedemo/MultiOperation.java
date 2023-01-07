package cn.johnnyzen.create.simpleFactory.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  21:10:21
 * @description: 简单工厂模式 - 具体产品
 */

public class MultiOperation extends AbstractOperation<Double> {
    @Override
    public Double getOperationResult(){
        return this.getNumberA() * this.getNumberB();
    }
}
