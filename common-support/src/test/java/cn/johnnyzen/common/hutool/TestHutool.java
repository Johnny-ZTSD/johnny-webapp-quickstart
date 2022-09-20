package cn.johnnyzen.common.hutool;

import cn.hutool.system.SystemUtil;
import cn.johnnyzen.common.util.debug.Print;


/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2021/3/18  01:35:40
 * @description: ...
 */

public class TestHutool {
    public static void main(String[] args) {
        //FileReader fileReader = new FileReader("C:\\Users\\Johnny\\Desktop\\study-tmp-urls.txt");
        //System.out.println(fileReader.readString());
        Print.print(SystemUtil.getRuntimeInfo());
        Print.print(SystemUtil.getOsInfo());

    }
}
