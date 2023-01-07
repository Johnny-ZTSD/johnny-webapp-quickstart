package cn.johnnyzen.strategy.storeDiscount;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/31  21:51:45
 * @description: 商场折扣活动问题
 */

public class Client {
    public static void main(String[] args) {
    double payableAmount = 400; //应付金额
    //CashContext cashContext = new CashContext(new NormalCash());//策略0
    //CashContext cashContext = new CashContext(new DiscountCash(0.8));//策略1
    //CashContext cashContext = new CashContext(new RebateCash(300, 150));//策略2
    //CashContext cashContext = new CashContext("discount:0.8");//策略3
    CashContext cashContext = new CashContext("rebate:300-100");//策略3
    double actualAmount = cashContext.getActualAmount(payableAmount); //实付金额

    System.out.println("应付金额:"+payableAmount+"\t"+"实付金额:" + actualAmount);
    }
}
