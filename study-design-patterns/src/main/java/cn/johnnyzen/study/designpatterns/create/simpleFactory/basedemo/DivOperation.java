package cn.johnnyzen.create.simpleFactory.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  21:08:07
 * @description: 简单工厂模式 - 具体产品
 */

public class DivOperation extends AbstractOperation<Double>  {
    @Override
    public Double getOperationResult(){
        if (this.getNumberB().doubleValue() == 0.0d ){
            throw new ArithmeticException(" number B must not be zero !");
        }
        return this.getNumberA() / this.getNumberB();
    }
}
