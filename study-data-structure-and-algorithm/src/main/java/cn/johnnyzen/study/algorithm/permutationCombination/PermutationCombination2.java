package cn.johnnyzen.study.algorithm.permutationCombination;

import java.util.Scanner;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/5/24  23:04:05
 * @description: 华为OD试题
 *  一排树，共有N颗
 *  其中死掉了[m1,m2,m3,...] 共计M颗树
 *  现在，准备在死掉的M颗树中补种 K 颗树
 *  求：全部的补种方案中，连续种树的最大长度
 */

public class PermutationCombination2 {
    /**
     * 补种前验证数据的有效性
     * @param originTreesSize 最初种的树的棵树 => 即 N
     * @param deadTreesSize 死亡树的棵树 => 即 M
     * @param surplusTreesSize 补种的树的个数 => 即 K
     * @return 经返回验证后， maxLength的初始值
     */
    public static int vaildate(int originTreesSize, int deadTreesSize, int surplusTreesSize){
        if(originTreesSize<0 || deadTreesSize<0 || surplusTreesSize <0){//负值
            throw new RuntimeException("Data is abnormal and negtive!");
        }
        if(deadTreesSize>originTreesSize){//死亡的树比总共的树还多
            throw new RuntimeException("There are more dead trees than total trees");
        }
        if(surplusTreesSize>deadTreesSize){//剩余的树比死亡的树多 => 死亡的树全部可以补种回来
            return originTreesSize;//maxLength = N = originTreesSize
        }
        return 0; //连续种树的最大长度(maxLength) 初始化为0;
    }
    public static Integer replantTree(int [] treesStatus,int startIndex, int endIndex, int surplus, Integer currentMaxLength){
        if(surplus==0){
            int maxLengthOfcurrentTreeStatus = collect(treesStatus);
            currentMaxLength = currentMaxLength<maxLengthOfcurrentTreeStatus?maxLengthOfcurrentTreeStatus:currentMaxLength;
            return currentMaxLength;
        }
        for(int i=startIndex; i<endIndex && surplus>0;i++){
            if(treesStatus[i] == 1){//当前这棵树 已死亡
                treesStatus[i] = 0; //尝试 在这个位置 补种1颗树
                surplus--; //剩余的树，少一颗

                currentMaxLength = replantTree(treesStatus, startIndex+1, endIndex, surplus, currentMaxLength);//递归，考虑种下一棵树

                treesStatus[i] = 1; //回溯
                surplus++;//回溯
            } else {
                continue;//跳过当前这棵树————考虑下一个死亡的树
            }
        }
        return currentMaxLength;
    }

    //收集一次当前种树的情况，获得当前状态下连续种树的最大长度
    public static int collect(int [] treesStatus){
        int maxLength = 0;
        int count=0;
        for(int i=1;i<treesStatus.length;i++){
            if(treesStatus[i] == 0){//当前这棵树活着
                count++;
                if(count>maxLength){
                    maxLength = count;
                }
            } else {//当前这棵树死了，重头开始计数
                count = 0;
            }
            //System.out.print("[" + i + "]:" + treesStatus[i] + " | ");
        }
        //System.out.println();
        return maxLength;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int [] treesStatus = new int[N+1]; //0 空置位; 初始时为0，表示该树仍活着（未死亡）
        int M = scanner.nextInt();
        for(int i=1;i<=M;i++){
            treesStatus[scanner.nextInt()] = 1; //该树死亡
        }
        int K = scanner.nextInt(); // 剩余的K颗树

        Integer maxLength = 0;//用包装类；否则 int作为基本数据类型，在调用函数过程中无法对其形参的原始内容进行改动。
        int validateResult = vaildate(N, M, K);
        if(validateResult>0){
            maxLength = validateResult;
        } else {
            maxLength = replantTree(treesStatus, 1, N,K, maxLength);
        }

        System.out.println(maxLength);
    }
}
/**
 input: N=8 / M=3{2,4,5} / K=2
 8
 3
 2 4 5
 2

 output: 6
 **/
