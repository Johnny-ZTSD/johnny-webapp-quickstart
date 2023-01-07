package cn.johnnyzen.study.algorithm.permutationCombination;

import java.util.Scanner;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/5/24  23:58:08
 * @description: [华为OD机试] 非负整数串中取出n个字符后组成的最小数
 *  https://www.nowcoder.com/discuss/945171
 */

public class PermutationCombination3 {
    /**
     * 参数合法性校验
     * @param line
     * @param deleteCharSize 待删除的字符个数 即 N
     */
    public static void validate(String line, int deleteCharSize){
        if(line.length() <= deleteCharSize){
            throw new RuntimeException("line.size <= deleteCharSize");
        }
    }

    public static Integer permutation(String line, boolean [] isDeletedChars,int startIndex, int endIndex, int restDeletedCharsSize, Integer currentMinValue){
        if(restDeletedCharsSize==0 || startIndex==endIndex){
            int minValue = collect(line, isDeletedChars);
            if(minValue<currentMinValue){
                currentMinValue = minValue;
            }
            return currentMinValue;
        }

        for(int i=startIndex;i<endIndex;i++){
            if(isDeletedChars[i] == false){//当前元素未被删除
                isDeletedChars[i] = true; //前进 => 尝试删除当前元素
                restDeletedCharsSize--; //前进 => 剩余的可删除的元素个数 - 1

                currentMinValue = permutation(line, isDeletedChars, startIndex, endIndex, restDeletedCharsSize, currentMinValue);//递归

                isDeletedChars[i] = false; //回溯
                restDeletedCharsSize++;//回溯
            }
        }
        return currentMinValue;
    }

    //收集一次当前状态的数值，获得当前状态下的最小值
    public static int collect(String line, boolean [] isDeletedChars){
        int minValue = Integer.MAX_VALUE;
        StringBuilder valueStr = new StringBuilder();
        for(int i=0;i<line.length();i++){
            if(isDeletedChars[i]==false){//当前元素未被删除
                valueStr.append(line.charAt(i));
            } else {//当前元素已被删除
                //step1 计算目前已组成的valueStr的值
                int value = 0;
                if(valueStr.length()>0){
                    value = Integer.parseInt(valueStr.toString());

                    if(value<minValue){//比当前最小值还小
                        minValue = value;
                    }
                }

                //step2 当前数据清空，重头统计
                //System.out.println("[clear.before] "+valueStr.toString());
                valueStr.delete(0, valueStr.length());
                //System.out.println("[clear.after] "+valueStr.toString());
            }
        }
        return minValue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        boolean [] isDeletedChars = new boolean[line.length()];//标记 当前 char 是否被删除；默认值（false）：未被删除
        int N = scanner.nextInt();

        validate(line, N);
        Integer minValue = Integer.MAX_VALUE;
        minValue = permutation(line, isDeletedChars, 0, line.length()-1, N,minValue);

        System.out.println(minValue);

    }
}
/**
input:
32443424282
3
output:
2
**/