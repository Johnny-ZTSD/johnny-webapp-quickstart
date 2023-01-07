package cn.johnnyzen.strategy.appShare.wechatStrategy;

import cn.johnnyzen.strategy.appShare.abstractStrategy.AbstractShare;
import cn.johnnyzen.strategy.appShare.util.YamlResourceBundleUtil;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/9/1  00:19:32
 * @description: ...
 */

public class WechatShare extends AbstractShare {
    private final static String ymlConfigFilePath = "share.yml";
    private static YamlResourceBundleUtil ymlUtil = null;

    static {
        ymlUtil = new YamlResourceBundleUtil();
    }

    @Override
    public String showTitle() {
        return (String) ymlUtil.getProperty(ymlUtil.loadYaml(ymlConfigFilePath), "share.wechat.title");
    }

    @Override
    public String showImgageUrl(String platform) {
        return (String) ymlUtil.getProperty(ymlUtil.loadYaml(ymlConfigFilePath), "share.wechat.imageUrl."+platform);
    }

    @Override
    public String showContent() {
        return (String) ymlUtil.getProperty(ymlUtil.loadYaml(ymlConfigFilePath), "share.wechat.content");
    }

    @Override
    public String showLink(String platform) {
        return (String) ymlUtil.getProperty(ymlUtil.loadYaml(ymlConfigFilePath), "share.wechat.link."+platform);
    }
}
