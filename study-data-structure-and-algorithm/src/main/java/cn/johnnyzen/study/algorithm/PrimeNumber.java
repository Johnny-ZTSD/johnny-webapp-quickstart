package cn.johnnyzen.study.algorithm;

import java.util.ArrayList;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/4/14  15:20:56
 * @description: 求素数
 */

public class PrimeNumber {
    public static void main(String[] args) {
        ArrayList<Integer> list = primeNumbers(100, 150);
        for (Integer i: list) {
            System.out.print(i +" ");
        }
        //101  103  107  109  113  121  127  131  137  139  143  149
    }
    //get all of prime numbers in [a, b]
    public static ArrayList<Integer> primeNumbers(int a, int b){
        ArrayList<Integer> resultList = new ArrayList<Integer>();
        for(int i=a; i<=b;i++){
            int square = (int) Math.sqrt(a);
            boolean isPrimeNumber = true;//默认是素数
            for(int j=2;j<=square;j++){
                if(i%j==0){// can be 整除: 不是素数
                    isPrimeNumber = false;
                    break;
                }
            }
            if(isPrimeNumber){
                resultList.add(i);
            }
        }
        return resultList;
    }
}
