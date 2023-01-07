package cn.johnnyzen.create.singleton.hungry.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  19:30:35
 * @description: 单例模式 - 饿汉式
 */

/**
 * final : 阻止派生类，派生可能会增加实例; 也避免后续新增类时，无端开支不必要的计算资源
 */
public final class HungrySingleton {
    /**
     * static + new classObject() : (首次/一开始)初始化时:私有属性instance就实例化(new)对象
     */
    private static HungrySingleton hungrySingleton = new HungrySingleton(); //

    /**
     * private : 私有化 类的构造函数 # 起到对外隐藏作用
     */
    private HungrySingleton(){
        System.out.println("I am a instance of the class 'HungrySingleton' !");
    }

    /**
     * static :
     *  1 非静态方法(getInstance)可调用静态属性(hungrySingleton);
     *    但调用时，可能尚未初始化，进而导致客户端出现空指针异常(NullPointerException)
     *  2 静态方法(getInstance)调用静态属性(hungrySingleton):
     *    在类初始化时就对静态属性进行了初始化/变量赋值，可避免上述问题发生
     */
    public static  HungrySingleton getInstance(){ //直接返回实例
        return hungrySingleton;
    }
}
