package cn.johnnyzen.create.simpleFactory.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  20:18:42
 * @description: 简单工厂模式 - 具体产品
 */

public class AddOpertation extends AbstractOperation<Double> {
    @Override
    public Double getOperationResult(){
        return  this.getNumberA() + this.getNumberB();
    }
}
