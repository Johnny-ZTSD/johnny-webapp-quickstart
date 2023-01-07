package cn.johnnyzen.create.simpleFactory.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  20:11:49
 * @description: 简单工厂模式 - 产品简单工厂
 */

public class OperationSimpleFacotry {
    public static AbstractOperation operation = null;

    /**
     * 创建具体产品
     * @return
     */
    public static AbstractOperation<Number> createOperation(String operationName) throws ClassNotFoundException {
        switch (operationName){
            case "+" :
                operation = new AddOpertation();
                break;
            case "-":
                operation = new SubOperation();
                break;
            case "x":
            case "*":
                operation = new MultiOperation();
                break;
            case "÷":
            case "/":
                operation = new DivOperation();
                break;
            default:
                throw new ClassNotFoundException("No this operation '"+operationName+"' Java Class!");
        }
        return operation;
    }
}
