package cn.johnnyzen.strategy.storeDiscount;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/31  22:04:39
 * @description: ...
 */

public class DiscountCash extends AbstractCash {
    private double discount = 1.0;

    public DiscountCash(double discount){
        if(discount<0 || discount>1){
            this.discount = 1;
        }
        this.discount = discount;
    }

    @Override
    public double acceptCash(double money) {
        return discount*money;
    }
}
