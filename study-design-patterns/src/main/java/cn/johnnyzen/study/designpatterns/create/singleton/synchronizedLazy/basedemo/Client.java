package cn.johnnyzen.create.singleton.synchronizedLazy.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  19:26:44
 * @description: 单例模式 - 懒汉式
 */

public class Client {
    public static void main(String[] args) {
        LazySingleton hungrySingleton = null;
        hungrySingleton = LazySingleton.getInstance();
    }
}
