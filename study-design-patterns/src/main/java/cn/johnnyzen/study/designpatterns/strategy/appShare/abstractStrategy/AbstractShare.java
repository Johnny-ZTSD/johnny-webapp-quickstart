package cn.johnnyzen.strategy.appShare.abstractStrategy;

import cn.johnnyzen.strategy.appShare.util.YamlResourceBundleUtil;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/9/1  00:20:20
 * @description: ...
 */

public abstract class AbstractShare {
    private String ymlFilePath = "share.yml";
    private YamlResourceBundleUtil ymlUtil;

    public abstract String showTitle();
    public abstract String showImgageUrl(String platform);
    public abstract String showContent();
    public abstract String showLink(String platform);
}
