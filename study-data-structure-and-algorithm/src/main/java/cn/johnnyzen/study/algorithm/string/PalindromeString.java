package cn.johnnyzen.study.algorithm.string;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/4/14  16:34:02
 * @description: 回文串
 */

public class PalindromeString {
    public static void main(String[] args) {
        System.out.println(isPalindromeStringWay2("aba"));
        System.out.println(isPalindromeStringWay2("abba"));
        System.out.println(isPalindromeStringWay2("ajka"));
    }

    /**
     * 判断一个字符串是否为回文串? ————方式1：原始算法
     */
    public static boolean isPalindromeStringWay1(String str){
        if(str==null) {
            return false;
        }
        int length = str.length();
        int t = length/2;
        for(int i=0;i<t;i++){
            if(str.charAt(i) != str.charAt(length-1-i)){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否为回文串? ————方式2：StringBuffer/StringBuffer#reverse()
     * String类中并没有提供翻转方法供我们使用，但 StringBuffer 和 StringBuilder 有reverse方法。
     */
    public static boolean isPalindromeStringWay2(String str){
        return (new StringBuilder(str)).reverse().toString().equals(str);
    }
}
