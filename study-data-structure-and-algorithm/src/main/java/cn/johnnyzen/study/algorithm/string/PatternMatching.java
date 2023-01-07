package cn.johnnyzen.study.algorithm.string;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/4/14  16:46:45
 * @description: 模式匹配
 */

public class PatternMatching {
    /**
     * 实现思路
     *      从目标S中的第1个字符开始和模式T中的的第1个比较（用  i  和 j 分别指示S串和T串中正在比较字符的位置），
     *          若相等，则：继续逐个比较后续字符，全部字符匹配完成后返回true(完全匹配)；否则， 返回 false(非完全匹配)
     * JDK相关源码: java.lang.String#equals(Object anObject)
     */
    public static boolean patternMatching(String strA, String strB){
        if(strA==null || strB==null || strA.length() != strB.length()){
            return false;
        }
        int i = 0,j = 0;
        int lengthA = strA.length(), lengthB = strB.length();
        while(i<lengthA && j<lengthB){
            if(strA.charAt(i) != strB.charAt(j)){
                System.out.println("[log] strA["+i+"] = "+strA.charAt(i)+" != "+"strB["+j+"] = "+strB.charAt(j));
                return false;
            }
            i++;
            j++;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(patternMatching("ababa", "ababa"));
        System.out.println(patternMatching("abababa", "ababa35"));
    }

}
