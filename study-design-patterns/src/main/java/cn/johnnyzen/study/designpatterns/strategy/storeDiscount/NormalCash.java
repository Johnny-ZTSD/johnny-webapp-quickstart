package cn.johnnyzen.strategy.storeDiscount;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/31  22:03:26
 * @description: ...
 */

public class NormalCash extends AbstractCash {

    @Override
    public double acceptCash(double money) {
        return money;
    }
}
