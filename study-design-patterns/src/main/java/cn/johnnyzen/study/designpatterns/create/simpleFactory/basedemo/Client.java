package cn.johnnyzen.create.simpleFactory.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  19:24:25
 * @description: 简单工厂模式
 */

public class Client {
    public static void main(String[] args) throws ClassNotFoundException {
        AbstractOperation operation = OperationSimpleFacotry.<Double>createOperation("/");
        operation.setNumberA(new Double(23.3) );
        operation.setNumberB(new Double(0.0000d) );
        System.out.println("Result:" + operation.getOperationResult());
    }
}
