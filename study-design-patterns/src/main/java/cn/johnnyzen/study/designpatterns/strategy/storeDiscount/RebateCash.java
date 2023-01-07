package cn.johnnyzen.strategy.storeDiscount;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/31  22:10:08
 * @description: ...
 */

public class RebateCash extends AbstractCash {
    private double totalAmount;
    private double returnAmount;

    public RebateCash(double totalAmount, double returnAmount){
        if(totalAmount<0.0d){
            totalAmount = 0.0d;
        }
        if(returnAmount<0.0d){
            returnAmount = 0.0d;
        }
        if(returnAmount>totalAmount){
            returnAmount = totalAmount;
        }
        this.totalAmount = totalAmount;
        this.returnAmount = returnAmount;
    }

    @Override
    public double acceptCash(double money) {
        if(money>=totalAmount){
            return money-returnAmount;
        }
        return money;
    }
}
