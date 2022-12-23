package cn.johnnyzen.common.util.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *@author johnnyzen
 *@version v1.0
 *@create-time 2022/10/06 14:46
 *@description ...
 *@reference-doc
 * [1]NacosConfig配置中心手动添加监听-CSDN-https://blog.csdn.net/qq_32434535/article/details/121353468
 * [2]Springbootnacos实现日志级别动态调整-CSDN-https://blog.csdn.net/sokia007/article/details/108245139
 */

public class NacosConfigListenerService {
        private static final Logger logger = LoggerFactory.getLogger(NacosConfigListenerService.class);

        private static final long DEFAULT_TIMEOUT = 5000L;

        private NacosConfigProperties nacosConfigProperties;

        public NacosConfigListenerService(){

        }

        public NacosConfigListenerService(NacosConfigProperties nacosConfigProperties){
            this.nacosConfigProperties = nacosConfigProperties;
        }

        public void initAndAddListener(String dataId, String group, Consumer<String> consumer) throws NacosException {
            initAndAddListener(dataId, group, consumer, false);
        }

        public void initAndAddListener(String dataId, String group, Consumer<String> consumer, boolean dataLog)
                throws NacosException {
            initAndAddListener(dataId, group, consumer, consumer, dataLog);
        }

        public void initAndAddListener(String dataId, String group, Consumer<String> initConsumer, Consumer<String> listenerConsumer) throws NacosException {
            initAndAddListener(dataId, group, initConsumer, listenerConsumer, false);
        }

        public void initAndAddListener(String dataId, String group, Consumer<String> initConsumer, Consumer<String> listenerConsumer, boolean dataLog) throws NacosException {
            initAndAddListener(dataId, group, DEFAULT_TIMEOUT, initConsumer, listenerConsumer, dataLog);
        }

        public void initAndAddListener(Properties properties, String dataId, String group, Consumer<String> consumer) throws NacosException {
            initAndAddListener(properties, dataId, group, DEFAULT_TIMEOUT, consumer, consumer, false);
        }

        public void initAndAddListener(String dataId, String group, long timeout, Consumer<String> initConsumer, Consumer<String> listenerConsumer, boolean dataLog) throws NacosException {
            initAndAddListener(getNacosProperties(), dataId, group, timeout, initConsumer, listenerConsumer, dataLog);
        }

        public void initAndAddListener(Properties properties, String dataId, String group, long timeout,
                                       Consumer<String> initConsumer, Consumer<String> listenerConsumer, boolean dataLog) throws NacosException {
            String config = getConfig(properties, dataId, group, timeout);
            logger.info("init config data: dataId:{},group:{}", dataId, group);
            if (dataLog) {
                initConsumer = logConsumer(dataId, group).andThen(initConsumer);
            }
            initConsumer.accept(config);
            addListener(properties, dataId, group, listenerConsumer, dataLog);
        }

        public ConfigService getConfigService(Properties properties) throws NacosException {
            return NacosFactory.createConfigService(properties);
        }

        public ConfigService getConfigService() throws NacosException {
            return getConfigService(getNacosProperties());
        }

        public String getConfig(String dataId, String group) throws NacosException {
            return getConfig(dataId, group, DEFAULT_TIMEOUT);
        }

        public String getConfig(String dataId, String group, Long timeout) throws NacosException {
            return getConfig(getNacosProperties(), dataId, group, timeout);
        }

        public String getConfig(Properties properties, String dataId, String group, Long timeout) throws NacosException {
            checkDataIdAndGroupBlank(dataId, group);
            logger.info("fetch config data: dataId:{},group:{}", dataId, group);
            if (timeout <= 0) {
                timeout = DEFAULT_TIMEOUT;
            }
            return getConfigService(properties).getConfig(dataId, group, timeout);
        }

        public <T> T getConfigAndParse(String dataId, String group, Function<String, T> parseFunction)
                throws NacosException {
            return getConfigAndParse(dataId, group, DEFAULT_TIMEOUT, parseFunction);
        }

        public <T> T getConfigAndParse(String dataId, String group, Long timeout, Function<String, T> parseFunction)
                throws NacosException {
            return getConfigAndParse(getNacosProperties(), dataId, group, timeout, parseFunction);
        }

        public <T> T getConfigAndParse(Properties properties, String dataId, String group, Long timeout,
                                       Function<String, T> parseFunction) throws NacosException {
            String config = getConfig(properties, dataId, group, timeout);
            return parseFunction.apply(config);
        }

        public void addListener(Properties properties, String dataId, String group, Listener listener)
                throws NacosException {
            logger.info("add a monitor for dataId:{},group:{}", dataId, group);
            getConfigService(properties).addListener(dataId, group, listener);
        }

        public void addListener(Properties properties, String dataId, String group, Consumer<String> consumer,
                                boolean dataLog) throws NacosException {
            checkDataIdAndGroupBlank(dataId, group);
            logger.info("add a monitor for dataId:{},group:{}", dataId, group);
            if (dataLog) {
                consumer = logConsumer(dataId, group).andThen(consumer);
            }
            getConfigService(properties).addListener(dataId, group, new NacosListener(consumer));
        }

        public void addListener(String dataId, String group, Listener listener) throws NacosException {
            addListener(getNacosProperties(), dataId, group, listener);
        }

        public void addListener(String dataId, String group, Consumer<String> consumer) throws NacosException {
            addListener(getNacosProperties(), dataId, group, consumer, true);
        }

        public boolean pushConfig(Properties properties, String dataId, String group, String config) throws NacosException {
            logger.info("push config info for dataId:{},group:{}", dataId, group);
            return getConfigService(properties).publishConfig(dataId, group, config);
        }

        public boolean pushConfig(String dataId, String group, String config) throws NacosException {
            return pushConfig(getNacosProperties(), dataId, group, config);
        }

        public void setNacosConfigProperties(NacosConfigProperties nacosConfigProperties){
            this.nacosConfigProperties = nacosConfigProperties;
        }

        public Properties getNacosProperties() {
            return nacosConfigProperties.assembleConfigServiceProperties();
        }

        private void checkDataIdAndGroupBlank(String dataId, String group) {
            if (StringUtils.isEmpty(dataId) || StringUtils.isEmpty(group)) {
                throw new RuntimeException("dataId or group is blank");
            }
        }

        private Consumer<String> logConsumer(String dataId, String group) {
            return conf -> logger.info("fetch config data for dataId:{},group:{} | config:{}", dataId, group, conf);
        }

        public static class NacosListener implements Listener {

            private final Consumer<String> doReceiveConfigInfoConsumer;

            public NacosListener(Consumer<String> doReceiveConfigInfoConsumer) {
                this.doReceiveConfigInfoConsumer = doReceiveConfigInfoConsumer;
            }

            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String config) {
                doReceiveConfigInfoConsumer.accept(config);
            }
        }
    }