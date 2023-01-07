package cn.johnnyzen.strategy.appShare.util;

//import com.simple.util.base.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: simple_tools
 * @description: yml配置处理工具
 * @author: ChenWenLong
 * @create: 2019-10-22 13:49
 **/
public class YamlResourceBundleUtil{

    /**
     * 功能描述:
     * 〈加载yml文件〉
     *
     * @params : [fileName]
     * @return : java.util.Map<?,?>
     * @author : cwl
     * @date : 2019/10/22 13:52
     */
    public Map<?, ?> loadYaml(String fileName) {
        InputStream in = YamlResourceBundleUtil.class.getClassLoader().getResourceAsStream(fileName);
        return StringUtils.isNotEmpty(fileName) ? (LinkedHashMap<?, ?>) new Yaml().load(in) : null;
    }

    /**
     * 功能描述:
     * 〈往yml文件中写数据,数据为map〉
     *
     * @params : [fileName, map]
     * @return : void
     * @author : cwl
     * @date : 2019/10/22 13:52
     */
    public void dumpYaml(String fileName, Map<?, ?> map) throws IOException {
        if (StringUtils.isNotEmpty(fileName)) {
            FileWriter fileWriter = new FileWriter(YamlResourceBundleUtil.class.getResource(fileName).getFile());
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            yaml.dump(map, fileWriter);
        }
    }

    /**
     * 功能描述:
     * 〈根据key查询yml中的数据〉
     *
     * @params : [map, qualifiedKey]
     * @return : java.lang.Object
     * @author : cwl
     * @date : 2019/10/22 13:53
     */
    public Object getProperty(Map<?, ?> map, Object qualifiedKey) {
        if (map != null && !map.isEmpty() && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (!"".equals(input)) {
                if (input.contains(".")) {
                    int index = input.indexOf(".");
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    return getProperty((Map<?, ?>) map.get(left), right);
                } else if (map.containsKey(input)) {
                    return map.get(input);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈设置yml中的值〉
     *
     * @params : [map, qualifiedKey, value]
     * @return : void
     * @author : cwl
     * @date : 2019/10/22 13:53
     */
    @SuppressWarnings("unchecked")
    public void setProperty(Map<?, ?> map, Object qualifiedKey, Object value) {
        if (map != null && !map.isEmpty() && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (!input.equals("")) {
                if (input.contains(".")) {
                    int index = input.indexOf(".");
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    setProperty((Map<?, ?>) map.get(left), right, value);
                } else {
                    ((Map<Object, Object>) map).put(qualifiedKey, value);
                }
            }
        }
    }

    //读yml文件
//    public void readYml(filePath){
//        File file = null;
//        file = new File(System.getProperty("user.dir"), "/src/com/neunn/monitor/web/utils/generates/ymlfiles/testYaml.yml");
//        try {
//            // 读取文件内容 (输入流)
//            FileInputStream out = new FileInputStream(file);
//            InputStreamReader isr = new InputStreamReader(out);
//            int ch = 0;
//            StringBuilder stringBuilder = new StringBuilder();
//            while ((ch = isr.read()) != -1) {
//                stringBuilder.append((char) ch);
//            }
//
//            System.out.println(stringBuilder);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
}