package cn.johnnyzen.create.singleton.hungry.basedemo;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  19:35:57
 * @description: ...
 */

public class Client {
    public static void main(String[] args) {
        HungrySingleton hungrySingleton = null;
        hungrySingleton = HungrySingleton.getInstance();
//        hungrySingleton = hungrySingleton.getInstance();
    }
}
