package cn.johnnyzen.create.singleton.synchronizedLazy.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  19:49:13
 * @description: 单例模式 - 懒汉式 - 双重检查加锁方式 (解决懒汉式单例的非线程安全问题)
 */

public class LazySingleton {
    /**
     * static + null : (首次)初始时，私有属性instance为null，不实例化(new)对象
     */
    private static LazySingleton lazySingleton = null;

    private LazySingleton(){
        /**
         * 如下5行代码为：
         *     为了易于模拟多线程下，懒汉式出现的问题：在创建实例的构造函数里，使当前线程暂停了50毫秒
         */
        try{
            Thread.sleep(50);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("I am a instance of the class 'LazySingleton' !");
    }

    /**
     * 双重检查加锁 解决懒汉式单例模式的多线程不安全问题
     * @return LazySingleton
     */
    public static LazySingleton getInstance(){
        if(lazySingleton==null){//避免懒汉式单例模式 首次实例化类对象时出现多线程环境下非线程安全问题
            synchronized (LazySingleton.class){//同步块，线程安全地创建实例
                if(lazySingleton==null){//再次检查实例是否存在？(第2次)如果不存在才真正地创建实例
                    lazySingleton = new LazySingleton();
                }
            }
        }
        return lazySingleton;
    }
}
