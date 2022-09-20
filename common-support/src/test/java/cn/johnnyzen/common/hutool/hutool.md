> http://www.mianshigee.com/tutorial/hutool/

# 核心（Hutool-core）
## 克隆
### 支持泛型的克隆接口和克隆类
``` txt
cn.hutool.clone.
    Cloneable 泛型克隆接口
    CloneSupport 泛型克隆类

ObjectUtil.cloneByStream(obj) 深克隆
```

## 类型转换
### 类型转换工具类-Convert
### 自定义类型转换-ConverterRegistry

## 日期时间
### 概述
### 日期时间工具-DateUtil
### 日期时间对象-DateTime
### 农历日期-ChineseDate
### LocalDateTime工具-LocalDateTimeUtil

## IO流相关
### 概述
### IO工具类-IoUtil
### 文件工具类-FileUtil
### 文件类型判断-FileTypeUtil
### 文件监听-WatchMonitor
### 文件
#### 文件读取-FileReader
#### 文件写入-FileWriter
#### 文件追加-FileAppender
#### 文件跟随-Tailer
#### 文件名工具-FileNameUtil
### 资源
#### 概述
#### 资源工具-ResourceUtil
#### ClassPath资源访问-ClassPathResource

## 工具类
### 概述
字符串工具-StrUtil
16进制工具-HexUtil
Escape工具-EscapeUtil
Hash算法-HashUtil
URL工具-URLUtil
XML工具-XmlUtil
对象工具-ObjectUtil
反射工具-ReflectUtil
泛型类型工具-TypeUtil
分页工具-PageUtil
剪贴板工具-ClipboardUtil
类工具-ClassUtil
类加载工具-ClassLoaderUtil
枚举工具-EnumUtil
命令行工具-RuntimeUtil
数字工具-NumberUtil
数组工具-ArrayUtil
随机工具-RandomUtil
唯一ID工具-IdUtil
压缩工具-ZipUtil
引用工具-ReferenceUtil
正则工具-ReUtil
身份证工具-IdcardUtil

## 语言特性
概述
HashMap扩展-Dict
单例工具-Singleton
断言-Assert
二进码十进数-BCD
控制台打印封装-Console
字段验证器-Validator
字符串格式化-StrFormatter
树结构
树结构工具-TreeUtil

## JavaBean
概述
Bean工具-BeanUtil
DynaBean
表达式解析-BeanPath

## 集合类
概述
集合工具-CollUtil
列表工具-ListUtil
Iterator工具-IterUtil
有界优先队列-BoundedPriorityQueue
线程安全的HashSet-ConcurrentHashSet

## Map
概述
Map工具-MapUtil
双向查找Map-BiMap
可重复键值Map-TableMap

## Codec编码
Base62编码解码-Base62
Base64编码解码-Base64
Base32编码解码-Base32

## 文本操作
CSV文件处理工具-CsvUtil
可复用字符串生成器-StrBuilder
Unicode编码转换工具-UnicodeUtil
字符串切割-StrSpliter

## 注解
注解工具-AnnotationUtil

## 比较器
概述
比较工具-CompareUtil

## 异常
异常工具-ExceptionUtil
其它异常封装

## 数学
数学相关-MathUtil

## 线程和并发
线程工具-ThreadUtil
自定义线程池-ExecutorBuilder
高并发测试-ConcurrencyTester

## 图片
图片工具-ImgUtil
图片编辑器-Img
## 网络
网络工具-NetUtil
URL生成器-UrlBuilder

# 配置文件(Hutool-setting）
概述
设置文件-Setting
Properties扩展-Props

# 日志（Hutool-log）
概述
日志工厂-LogFactory
静态调用日志-StaticLog
疑惑解答

# 缓存（Hutool-cache）
概述
缓存工具-CacheUtil
先入先出-FIFOCache
最少使用-LFUCache
最近最久未使用-LRUCache
超时-TimedCache
弱引用-WeakCache
文件缓存-FileCache

# JSON（Hutool-json）
概述
JSON工具-JSONUtil
JSON对象-JSONObject
JSON数组-JSONArray

# 加密解密（Hutool-crypto）
概述
加密解密工具-SecureUtil
对称加密-SymmetricCrypto
非对称加密-AsymmetricCrypto
摘要加密-Digester
消息认证码算法-HMac
签名和验证-Sign
国密算法工具-SmUtil

# DFA查找（Hutool-dfa）
概述
DFA查找

# 数据库（Hutool-db）
概述
数据库简单操作-Db
支持事务的CRUD-Session
数据源配置db.setting样例
数据源工厂-DsFactory
SQL执行器-SqlExecutor
案例1-导出Blob字段图像
常见问题
NoSQL数据库客户端封装
Redis客户端封装-RedisDS
MongoDB客户端封装-MongoDS

# HTTP客户端（Hutool-http）
概述
Http客户端工具类-HttpUtil
Http请求-HttpRequest
Http响应-HttpResponse
HTML工具类-HtmlUtil
UA工具类-UserAgentUtil
常用Http状态码-HttpStatus
常见问题
案例1-爬取开源中国的开源资讯
WebService
Soap客户端-SoapClient
Server
简易Http服务器-SimpleServer

# 定时任务（Hutool-cron）
概述
全局定时任务-CronUtil

# 扩展（Hutool-extra）
概述
邮件工具-MailUtil
二维码工具-QrCodeUtil
Servlet工具-ServletUtil
模板引擎
模板引擎封装-TemplateUtil
Jsch封装
Jsch(SSH)工具-JschUtil
CommonsNet封装
FTP封装-Ftp
Emoji表情
Emoji工具-EmojiUtil
中文分词
中文分词封装-TokenizerUtil
Spring
Spring工具-SpringUtil
Cglib
Cglib工具-CglibUtil
拼音
拼音工具-PinyinUtil

# 布隆过滤（Hutool-bloomFilter）
概述

# 切面（Hutool-aop）
概述
切面代理工具-ProxyUtil

# 脚本（Hutool-script）
概述
Script工具-ScriptUtil

# Office文档操作（Hutool-poi）
概述
Excel工具-ExcelUtil
Excel读取-ExcelReader
流方式读取Excel2003-Excel03SaxReader
流方式读取Excel2007-Excel07SaxReader
Excel生成-ExcelWriter
Excel大数据生成-BigExcelWriter
Word生成-Word07Writer

# 系统调用（Hutool-system）
系统属性调用-SystemUtil

# 图形验证码（Hutool-captcha）
概述

# 网络Socket（Hutool-socket）
概述
NIO封装-NioServer和NioClient
AIO封装-AioServer和AioClient