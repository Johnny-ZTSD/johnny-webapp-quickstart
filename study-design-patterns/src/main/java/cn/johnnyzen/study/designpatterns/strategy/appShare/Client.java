package cn.johnnyzen.strategy.appShare;

import cn.johnnyzen.strategy.appShare.abstractStrategy.AbstractShare;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/9/1  00:16:31
 * @description:
 * 在我们公司的应用程序中有一个app分享功能。
 * 目前暂定可以分享到 【朋友圈，微信好友，sina，qq】四个地方，分享所需的内容包含 【标题， 分享图片，分享内容， 分享链接】；
 * 产品经理不能确定是否后续会添加新的 分享入口，比如 支付宝 ，qq空间；
 * 对于产品的内容也不是固定的，也许会增加其他内容。
 * 如果我们按常规设计类，我们要设计四个类；
 * 如果内容模版有变动需要在方法中修改；
 * 如果加入了其他内容属性，之前设计的代码时间就浪费了。
 *
 * 既然是分享模版可以当成一种算法策略，笔者就联想到了使用策略模式。
 *
 * 作者：早睡的比熊
 * 链接：https://www.jianshu.com/p/55d4c0898ef4
 */

public class Client {
    public static void main(String[] args) {
        String platform = "ios";
        AbstractShare share = ShareContext.getShare("shareType");
        System.out.println("share title:"+share.showTitle());
        System.out.println("share content:"+share.showContent());
        System.out.println("share link:"+share.showLink(platform));
        System.out.println("share image Url:"+share.showImgageUrl(platform));
    }

}
