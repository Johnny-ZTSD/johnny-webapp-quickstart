package cn.johnnyzen.common.util.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/8/13  11:27:17
 * @description: ...
 */

public class StringUtil {
    private static final Logger LOG = LoggerFactory.getLogger(StringUtil.class);

    /**
     * 去除指定 markdown 文本的空格，但保留换行符等其他符号
     */
    public static String trimBlankForMarkdwonFile(String filePath){
        StringBuilder stringBuilder = new StringBuilder();

        Set<String> markdownCharset = new HashSet<>();
        String codeSnippetCharacter = "```";
        int countCodeSnippetCharacter = 0;
        markdownCharset.add("#");
        markdownCharset.add(">");
        markdownCharset.add("+");
        markdownCharset.add("-");
        markdownCharset.add(codeSnippetCharacter);

        try (Scanner sc = new Scanner(new FileReader(filePath))) {
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if(line.replace(" ", "").length()>1){//非空行 且 在长度上可能为 含有 md 特殊字符的行
                    String startChar = line.replace("\t","").replace(" ","").substring(0, 1);// 解决: "    +"、"    -" 的问题
                    //LOG.debug("startChar: {}", startChar);
                    if(!markdownCharset.contains(startChar)){// 非 含有 markdown 特殊字符的行
                        if(line.startsWith(codeSnippetCharacter)){
                            countCodeSnippetCharacter += 1; //标记 代码片段字符的起始行
                        } else  {
                            if(countCodeSnippetCharacter%2==0){// 非 代码片段字符行
                                line = line.replace(" ", "");
                            }
                        }
                    }
                }
                stringBuilder.append( line+ "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * 求字符串差集
     * 注1:方向，仅支持从前往后求差集
     * 注2:必须满足 一个字符串是另一个字符串的子集，如果不是子集，结果将出错
     *
     * @param strA
     * @param strB
     */
    public static String minus(String strA, String strB) {
        if (strA == null) {
            return null;
        }
        if (strB == null) {
            return null;
        }
        int compare = strA.compareTo(strB);
        LOG.debug("compare:" + (compare) + " <" + strA + " : " + strB + ">");
        int lenA = strA.length();
        int lenB = strB.length();
        if (compare == 0) { //相等
            return "";
        } else if (compare < 0) {//A更小或者更短
            return strB.substring(lenB + compare);
        } else {//B更长或者更大
            return strA.substring(lenA - compare);
        }
    }
}
