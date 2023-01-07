package cn.johnnyzen.strategy.storeDiscount;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/31  21:58:14
 * @description: ...
 */

public class CashContext {
    private AbstractCash cash = null;

    public CashContext(AbstractCash cash){
        this.cash = cash;
    }

    public CashContext(String rebateStrategy){//策略模式 与 简单工厂模式 结合
        switch (rebateStrategy){
            case "normal":
                this.cash = new NormalCash();
                break;
            case "discount:0.8":
                this.cash = new DiscountCash(0.8);
                break;
            case "rebate:300-100":
                this.cash = new RebateCash(300, 100);
                break;
            default:
                this.cash = new NormalCash();
                break;
        }
    }

    public double getActualAmount(double money){
        return cash.acceptCash(money);
    }
}
