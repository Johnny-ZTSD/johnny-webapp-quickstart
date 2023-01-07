package cn.johnnyzen.study.algorithm.permutationCombination;

import java.util.*;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/5/24  09:42:02
 * @description: 排列组合算法
 *  【排列】：从给定个数的元素中取出指定个数的元素，进行排序
 *  【组合】：从给定个数的元素中取出指定个数的元素，不考虑排序
 *
 *  根据组合学研究与【发展现状】，它可以分为如下几个【分支】：
 *  + 经典组合学
 *  + 组合设计
 *  + 组合序
 *  + 图与超图
 *  + 组合多面形与最优化
 *
 *  排列组合的【中心问题】是：研究给定要求的排列和组合可能出现的【情况总数】
 *
 *  排列组合与【古典概率论】关系密切，近20年来用组合学的方法已经解决了一些即使在整个数学领域也是具有挑战性的难题
 */

/**
 * 没有重复项的全排列
 * https://www.nowcoder.com/practice/4bcf3081067a4d028f95acee3ddcd2b1
 * @param <T>
 */
public class PermutationCombination<T> {
    //没有重复项的全排列
    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<ArrayList<Integer>> list = new ArrayList();
        return permutation(num, 0, num.length-1, list);
    }

    private ArrayList<ArrayList<Integer>> permutation(int [] array, int startIndex, int endIndex, ArrayList<ArrayList<Integer>> list){
        if(startIndex == endIndex){//递归的终止条件 | 收集1次排列的时机
            list.add(collect(array));
            return list;
        }
        for(int i = startIndex;i<=endIndex;i++){
            swap(array, startIndex, i);
            permutation(array, startIndex+1, endIndex, list);//递归
            swap(array, startIndex, i);//回溯回原状（恢复原始状态）
        }
        return list;
    }

    private void swap(int [] array, int index1, int index2){
        int tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }

    //收集1次当前的排列
    private ArrayList<Integer> collect(int [] array){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i =0;i<array.length;i++){
            list.add(array[i]);
        }
        return list;
    }

    /**
     * @description 题目描述
     * 给出一组数字，返回该组数字的所有排列
     * 例如：
     * [1,2,3]的所有排列如下
     * [1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2], [3,2,1].
     * （以数字在数组中的位置靠前为优先级，按字典序排列输出。）
     *
     * 数据范围：数字个数 0 < n \le 60<n≤6
     * 要求：空间复杂度 O(n!)O(n!) ，时间复杂度 O(n!）O(n!）
     * @example
     *  input [1,2,3]
     *  output [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * @param args
     */
    public static void main(String[] args) {

    }
}
