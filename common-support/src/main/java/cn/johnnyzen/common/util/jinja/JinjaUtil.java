package cn.seres.bd.dataservice.common.utils;

import cn.seres.bd.dataservice.common.exception.BusinessException;
import cn.seres.bd.dataservice.common.exception.CommonErrEnum;
import cn.seres.bd.dataservice.common.exception.CommonException;
import com.hubspot.jinjava.Jinjava;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 使用jinja模板转换方法
 *
 * @author lw / zengtai
 * @since 2022/07/15
 *  http://docs.jinkan.org/docs/jinja2/templates.html [推荐]
 *  http://docs.jinkan.org/docs/jinja2/templates.html#for [推荐]
 *  https://product.hubspot.com/blog/jinjava-a-jinja-for-your-java
 *  https://github.com/HubSpot/jinjava/blob/master/benchmark/resources/jinja/simple.jinja
 *  https://blog.csdn.net/a232884c/article/details/121982059
 *  https://wenku.baidu.com/view/152c14280440be1e650e52ea551810a6f524c806.html
 */
@Slf4j
public class JinjaUtil {
    /**
     * 异常标志
     * @description 在通过本工具解析完成内容模板后，可通过判定结果文本中存在此异常标志。
     */
    private final static String EXCEPTION_FLAG = "THROW_RUNETIME_EXCEPTION";

    /**
     *
     * @param template
     * @param properties
     * @return
     */
    public static String jinjaConvert(String template, Properties properties) {
        return jinjaConvert(template, properties);
    }

    /**
     *
     * @param template
     * @param properties
     * @return
     */
    public static String jinjaConvert(String template, Map properties) {
        return jinjaConvert(template, properties, false);
    }

    public static String jinjaConvert(String template, Map properties, boolean isLog) {
        Jinjava jinjava = new Jinjava();
        if(isLog){
            log.debug("template: {}", template);
        }
        String renderedResultText = jinjava.render(template, properties);
        if(isLog){
            log.debug("renderedResultText: {}", renderedResultText);
        }
        checkExceptionInRenderedResultText(renderedResultText, template, properties);
        return renderedResultText;
    }

    /**
     * 检查渲染后的结果文本的异常情况
     * @param renderedResultText 检查对象 := 渲染后的结果文本
     * @param template 渲染前的模板文本
     * @param properties 变量属性集合
     */
    private static final void checkExceptionInRenderedResultText(String renderedResultText, String template, Map properties){
        if(renderedResultText.contains(EXCEPTION_FLAG)){
            String errorMessageTemplate = "errorCode: %s,\n errorName: %s,\n errorCause: %s,\n template: %s,\n properties: %s";
            throw new BusinessException(
                    new BusinessException(
                            CommonErrEnum.JINJA_TEMPLATE_TRANS_ERR.getCode(),
                            CommonErrEnum.JINJA_TEMPLATE_TRANS_ERR.getMsg(),
                            renderedResultText,
                            template,
                            properties
                    ),
                    CommonErrEnum.JINJA_TEMPLATE_TRANS_ERR.getCode(),
                    CommonErrEnum.JINJA_TEMPLATE_TRANS_ERR.getMsg() + "(renderedResultText:" + renderedResultText + ")"
            );
        }
    }
}
