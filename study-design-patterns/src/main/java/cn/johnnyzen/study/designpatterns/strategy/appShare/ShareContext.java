package cn.johnnyzen.strategy.appShare;

import cn.johnnyzen.strategy.appShare.abstractStrategy.AbstractShare;
import cn.johnnyzen.strategy.appShare.util.YamlResourceBundleUtil;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/9/1  00:37:07
 * @description: ...
 */

public class ShareContext {
    private static AbstractShare share = null;

    public static AbstractShare getShare(String shareType){
        YamlResourceBundleUtil ymlUtil = new YamlResourceBundleUtil();
        String className = (String) ymlUtil.getProperty(ymlUtil.loadYaml("share.yml"), shareType);
        Class cls;
        try {
            cls = Class.forName(className); //加载 配置文件中指定的 具体(实现)策略类
            share = (AbstractShare) cls.newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return share;
    }
}
