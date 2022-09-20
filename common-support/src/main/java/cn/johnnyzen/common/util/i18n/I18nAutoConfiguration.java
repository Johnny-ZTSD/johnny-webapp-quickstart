package cn.johnnyzen.common.util.i18n;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
//@ConditionalOnProperty(
//        value = {"cn.johnnyzen.common.i18n.enable"},
//        havingValue = "true"
//)
class I18nAutoConfiguration implements BeanFactoryPostProcessor, BeanClassLoaderAware {
    /** org.springframework.context.MessageSource **/
    private static final String MESSAGE_SOURCE_BEAN_NAME = "messageSource";
    private ClassLoader beanClassLoader;

    I18nAutoConfiguration() {
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        Properties commonMessages = new Properties();

        try {
            PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver(new DefaultResourceLoader(this.beanClassLoader));
            Pattern pattern = Pattern.compile("messages_(.+?)\\.properties");
            Resource[] resources = patternResolver.getResources("classpath*:i18n/messages*.properties");
            Arrays.stream(resources).forEach((resource) -> {
                try {
                    Properties properties = new Properties();
                    Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8");
                    Throwable var6 = null;

                    try {
                        properties.load(reader);
                    } catch (Throwable var16) {
                        var6 = var16;
                        throw var16;
                    } finally {
                        if (reader != null) {
                            if (var6 != null) {
                                try {
                                    reader.close();
                                } catch (Throwable var15) {
                                    var6.addSuppressed(var15);
                                }
                            } else {
                                reader.close();
                            }
                        }

                    }

                    Locale locale = null;
                    Matcher matcher = pattern.matcher(resource.getFilename());
                    if (matcher.matches()) {
                        locale = Locale.forLanguageTag(matcher.group(1).replace('_', '-'));
                    }

                    if (locale != null) {
                        Set<String> propertyNames = properties.stringPropertyNames();
                        Iterator var8 = propertyNames.iterator();

                        while (var8.hasNext()) {
                            String propertyName = (String) var8.next();
                            messageSource.addMessage(propertyName, locale, properties.getProperty(propertyName));
                        }
                    } else {
                        commonMessages.putAll(properties);
                    }
                } catch (IOException var18) {
                }

            });
        } catch (IOException var7) {
        }

        messageSource.setCommonMessages(commonMessages);
        if (beanFactory.containsLocalBean("messageSource")) {
            ((DefaultListableBeanFactory) beanFactory).removeBeanDefinition("messageSource");
            messageSource.setParentMessageSource((MessageSource) beanFactory.getBean("messageSource", MessageSource.class));
        }

        beanFactory.registerSingleton("messageSource", messageSource);
        beanFactory.registerSingleton(LocalMessage.class.getName(), new I18nAutoConfiguration.MessageSourceLocalMessage(messageSource));
    }

    private class MessageSourceLocalMessage implements LocalMessage {
        private final MessageSource messageSource;

        public MessageSourceLocalMessage(MessageSource messageSource) {
            this.messageSource = messageSource;
        }

        @Override
        public String get(String key) {
            return this.get(key, (String) null);
        }

        @Override
        public String getOrDef(String key, String def) {
            return this.get(key, (Object[]) null, def);
        }

        @Override
        public String get(String key, Object[] args, String def) {
            return this.messageSource.getMessage(key, args, def, LocaleContextHolder.getLocale());
        }

        @Override
        public String get(String key, Object... args) {
            return this.get(key, args, (String) null);
        }

        @Override
        public String getOrDef(String key, String def, Object... args) {
            return this.get(key, args, def);
        }
    }
}

