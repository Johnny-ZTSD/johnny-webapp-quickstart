package cn.johnnyzen.common.config;

import cn.johnnyzen.common.util.i18n.LocalMessageUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/9/1  00:17:47
 * @description: ...
 */
@Configuration
public class SpringBeanConfig {
    @Bean
    public LocalMessageUtil localMessageUtil(){
        return new LocalMessageUtil();
    }
}
