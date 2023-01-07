package cn.johnnyzen.study.algorithm.string;

import java.util.Stack;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/4/24  20:22:06
 * @description: 进制间转换
 */

public class ConversionNumber {
    //10进制数 转 K 进制
    public static String transerToKNumber(int n, int k){
        Stack stack = new Stack();
        while(k<=n){
            stack.push(n%k);
            n = n/k;
        }
        stack.push(n);
        StringBuilder resultStringBuilder = new StringBuilder();

        while(!stack.empty()){
            resultStringBuilder.append(stack.pop());
        }
        return resultStringBuilder.toString();
    }

    public static void main(String[] args) {
        int n = 18; // 10进制数
        int k = 2; // k 进制
        System.out.println(transerToKNumber(n, k));//10010
    }
}
