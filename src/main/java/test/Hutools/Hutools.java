package test.Hutools;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.file.LFUFileCache;
import cn.hutool.cache.file.LRUFileCache;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.cache.impl.WeakCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.*;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.*;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.nosql.redis.RedisDS;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.Engine;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.StaticLog;
import cn.hutool.log.dialect.commons.ApacheCommonsLogFactory;
import cn.hutool.log.dialect.console.ConsoleLogFactory;
import cn.hutool.log.dialect.jdk.JdkLogFactory;
import cn.hutool.log.level.Level;
import redis.clients.jedis.Jedis;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.security.KeyPair;
import java.util.*;
import java.util.List;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/2
 * @描述
 */
public class Hutools {

    private static final Log log = LogFactory.get("我是一个自定义日志名");


    public static void main(String[] args) throws Exception {
        //boolean json = JSONUtil.isJson("{asdfs:\"asdfs\"}");
        //System.out.println(json);
        //Map<String,Object> resultMap = new HashMap1<String, Object>();
        //resultMap.put("name","张三");
        //resultMap.put("age","15");
        //String name = "李四";
        //int age = 25;
        //String format = StrUtil.format("你好{}，我{}岁了", name,age);
        //System.out.println(format);
        //String post = HttpUtil.post("www.baidu.com", resultMap);
        //String s = HttpUtil.get("https://www.baidu.com");
        //JSONObject jsonObject = JSONUtil.parseObj(post);
        //System.out.println(jsonObject.toString());
        //System.out.println(s);



        /*String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
//使用正则获取所有标题
        List<String> titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            Console.log(title);
        }*/

        // 最简单的HTTP请求，可以自动通过header等信息判断编码，不区分HTTP和HTTPS
        /*String result1= HttpUtil.get("https://www.baidu.com");


//可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap1<String, Object> paramMap = new HashMap1<String, Object>();
        paramMap.put("city", "北京");
        String result3= HttpUtil.get("https://www.baidu.com", paramMap);

        System.out.println(result1);
        System.out.println(result3);*/

        /*Jedis jedis = RedisDS.create().getJedis();
        jedis.set("aa","bb");
        String aa = jedis.get("aa");
        System.out.println(aa);*/

        /*增
        Db.use().insert(
                Entity.create("t_user")
                        .set("account", "unitTestUser")
                        .set("password","123456")
                        .set("username","admin")
                        .set("status","active")
                        .set("created",new Date())
        );*/
        //插入数据并返回自增主键：

        /*long i = Db.use().insertForGeneratedKey(
                Entity.create("t_user")
                        .set("account", "unitTestUser2")
                        .set("password","123456")
                        .set("username","admin2")
                        .set("status","active")
                        .set("created",new Date())
        );
        System.out.println(i);*/

        /*Db.use().del(
                Entity.create("t_user").set("account", "unitTestUser2")//where条件
        );*/

        //以AES算法为例：
        /*String content = "test中文";

//随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();

//构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

//加密
        byte[] encrypt = aes.encrypt(content);
        System.out.println(new String(encrypt,"utf-8"));
//解密
        byte[] decrypt = aes.decrypt(encrypt);
        System.out.println(new String(decrypt));
//加密为16进制表示
        String encryptHex = aes.encryptHex(content);
        System.out.println(encryptHex);
//解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);*/

        //DESede实现
        /*String content = "test中文";

        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue()).getEncoded();

        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DESede, key);

//加密
        byte[] encrypt = des.encrypt(content);
//解密
        byte[] decrypt = des.decrypt(encrypt);

//加密为16进制字符串（Hex表示）
        String encryptHex = des.encryptHex(content);
        System.out.println(encryptHex);
//解密为字符串
        String decryptStr = des.decryptStr(encryptHex);
        System.out.println(decryptStr);*/

        //AES封装
        /*String content1 = "test中文";

// 随机生成密钥
        byte[] key1 = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();

// 构建
        AES aes1 = SecureUtil.aes(key1);

// 加密
        byte[] encrypt1 = aes1.encrypt(content1);
// 解密
        byte[] decrypt1 = aes1.decrypt(encrypt1);

// 加密为16进制表示
        String encryptHex1 = aes1.encryptHex(content1);
        System.out.println(encryptHex1);
// 解密为字符串
        String decryptStr1 = aes1.decryptStr(encryptHex1, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr1);*/

        //自定义模式和偏移
        /*String content1 = "test中文";

// 随机生成密钥
        byte[] key1 = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();

// 构建
        AES aes1 = new AES(Mode.CTS, Padding.PKCS5Padding, "0CoJUm6Qyw8W8jud".getBytes(), "0102030405060708".getBytes());

// 加密
        byte[] encrypt1 = aes1.encrypt(content1);
// 解密
        byte[] decrypt1 = aes1.decrypt(encrypt1);

// 加密为16进制表示
        String encryptHex1 = aes1.encryptHex(content1);
        System.out.println(encryptHex1);
// 解密为字符串
        String decryptStr1 = aes1.decryptStr(encryptHex1, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr1);*/

        //DES封装
        /*String content1 = "test中文";

// 随机生成密钥
        //byte[] key1 = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();

// 构建
        *//*byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue()).getEncoded();
        DES des1 = SecureUtil.des(key);*//*
        //自定义模式和偏移
        DES des = new DES(Mode.CTS, Padding.PKCS5Padding, "0CoJUm6Qyw8W8jud".getBytes(), "01020304".getBytes());
// 加密
        byte[] encrypt1 = des.encrypt(content1);
// 解密
        byte[] decrypt1 = des.decrypt(encrypt1);

// 加密为16进制表示
        String encryptHex1 = des.encryptHex(content1);
        System.out.println(encryptHex1);
// 解密为字符串
        String decryptStr1 = des.decryptStr(encryptHex1, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr1);*/

        /*RSA rsa = new RSA();

//获得私钥
        rsa.getPrivateKey();
        rsa.getPrivateKeyBase64();
//获得公钥
        rsa.getPublicKey();
        rsa.getPublicKeyBase64();

//公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);

//Junit单元测试
        System.out.println("我是一段测试aaaa".equals(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8)));


//私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);*/

//Junit单元测试
//Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));


        /*RSA rsa = new RSA();
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        pair.getPrivate();
        pair.getPublic();
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);

        System.out.println("我是一段测试aaaa".equals(StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8)));*/

        /*String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIL7pbQ+5KKGYRhw7jE31hmA"
                + "f8Q60ybd+xZuRmuO5kOFBRqXGxKTQ9TfQI+aMW+0lw/kibKzaD/EKV91107xE384qOy6IcuBfaR5lv39OcoqNZ"
                + "5l+Dah5ABGnVkBP9fKOFhPgghBknTRo0/rZFGI6Q1UHXb+4atP++LNFlDymJcPAgMBAAECgYBammGb1alndta"
                + "xBmTtLLdveoBmp14p04D8mhkiC33iFKBcLUvvxGg2Vpuc+cbagyu/NZG+R/WDrlgEDUp6861M5BeFN0L9O4hz"
                + "GAEn8xyTE96f8sh4VlRmBOvVdwZqRO+ilkOM96+KL88A9RKdp8V2tna7TM6oI3LHDyf/JBoXaQJBAMcVN7fKlYP"
                + "Skzfh/yZzW2fmC0ZNg/qaW8Oa/wfDxlWjgnS0p/EKWZ8BxjR/d199L3i/KMaGdfpaWbYZLvYENqUCQQCobjsuCW"
                + "nlZhcWajjzpsSuy8/bICVEpUax1fUZ58Mq69CQXfaZemD9Ar4omzuEAAs2/uee3kt3AvCBaeq05NyjAkBme8SwB0iK"
                + "kLcaeGuJlq7CQIkjSrobIqUEf+CzVZPe+AorG+isS+Cw2w/2bHu+G0p5xSYvdH59P0+ZT0N+f9LFAkA6v3Ae56OrI"
                + "wfMhrJksfeKbIaMjNLS9b8JynIaXg9iCiyOHmgkMl5gAbPoH/ULXqSKwzBw5mJ2GW1gBlyaSfV3AkA/RJC+adIjsRGg"
                + "JOkiRjSmPpGv3FOhl9fsBPjupZBEIuoMWOC8GXK/73DHxwmfNmN7C9+sIi4RBcjEeQ5F5FHZ";

        RSA rsa = new RSA(PRIVATE_KEY, null);

        String a = "2707F9FD4288CEF302C972058712F24A5F3EC62C5A14AD2FC59DAB93503AA0FA17113A020EE4EA35EB53F"
                + "75F36564BA1DABAA20F3B90FD39315C30E68FE8A1803B36C29029B23EB612C06ACF3A34BE815074F5EB5AA3A"
                + "C0C8832EC42DA725B4E1C38EF4EA1B85904F8B10B2D62EA782B813229F9090E6F7394E42E6F44494BB8";

        byte[] aByte = HexUtil.decodeHex(a);
        byte[] decrypt = rsa.decrypt(aByte, KeyType.PrivateKey);
        System.out.println("虎头闯杭州,多抬头看天,切勿只管种地".equals(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8)));*/
//Junit单元测试
//Assert.assertEquals("虎头闯杭州,多抬头看天,切勿只管种地", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

        /*byte[] data = "我是一段测试字符串".getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
//签名
        byte[] signed = sign.sign(data);
//验证签名
        boolean verify = sign.verify(data, signed);
        System.out.println(verify);*/


        /*BufferedInputStream in = FileUtil.getInputStream("C:\\Users\\super\\Desktop\\jenkins搭建.docx");
        BufferedOutputStream out = FileUtil.getOutputStream("C:\\Users\\super\\Desktop\\jenkins搭建2.docx");
        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
        System.out.println(copySize);*/

        /*File file = FileUtil.file("C:\\Users\\super\\Desktop\\QQ截图20190703202809.png");
        String type = FileTypeUtil.getType(file);
//输出 jpg则说明确实为jpg文件
        Console.log(type);*/

        /*File file = FileUtil.file("C:\\Users\\super\\Desktop\\QQ截图20190703202809.png");
//这里只监听文件或目录的修改事件
        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
        watchMonitor.setWatcher(new Watcher(){
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("创建：{}-> {}", currentPath, obj);
            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("修改：{}-> {}", currentPath, obj);
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("删除：{}-> {}", currentPath, obj);
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("Overflow：{}-> {}", currentPath, obj);
            }
        });

//设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth(3);
//启动监听
        watchMonitor.start();*/

        /*File file = FileUtil.file("C:\\Users\\super\\Desktop\\Q图20111212121190703202809.png");
        WatchMonitor.createAll(file, new SimpleWatcher(){
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Console.log("EVENT modify");
            }
        }).start();*/

        /*ClassPathResource resource = new ClassPathResource("config/file2.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());
        Console.log("Properties: {}", properties);
        String aa = properties.getProperty("fff");
        System.out.println(aa);*/

        //默认UTF-8编码，可以在构造中传入第二个参数做为编码
        /*FileReader fileReader = new FileReader("file.properties");
        String result = fileReader.readString();
        System.out.println(result);*/

        /*FileWriter writer = new FileWriter("file.properties");
        writer.write("hhh=fff");*/

        /*Integer[] a = {1,2,3,4,5,6};
        Integer[] filter = ArrayUtil.filter(a, new Editor<Integer>(){
            @Override
            public Integer edit(Integer t) {
                return (t % 2 == 0) ? t : null;
            }});
        //Assert.assertArrayEquals(filter, new Integer[]{2,4,6});
        System.out.println(filter.toString());*/

        //根据long值获取ip v4地址
        /*long iplong = 2130706433L;
        String ip= NetUtil.longToIpv4(iplong);
        System.out.println(ip);

//根据ip地址计算出long型的数据
        long ip1= NetUtil.ipv4ToLong(ip);

//检测本地端口可用性
        boolean result= NetUtil.isUsableLocalPort(6379);
        System.out.println(result);

//是否为有效的端口
        boolean result1= NetUtil.isValidPort(6379);
        System.out.println(result1);

//隐藏掉IP地址
        String result2 =NetUtil.hideIpPart(ip);
        System.out.println(result2);*/

        /*double te1=123456.123456;
        double te2=123456.128456;
        Console.log(NumberUtil.round(te1,4));//结果:123456.1235
        Console.log(NumberUtil.round(te2,4));//结果:123456.1285*/

        /*double te1=123456.123456;
        double te2=123456.128456;
        Console.log(NumberUtil.roundStr(te1,2));//结果:123456.12
        Console.log(NumberUtil.roundStr(te2,2));//结果:123456.13*/

        /**
         * 0 -> 取一位整数
         * 0.00 -> 取一位整数和两位小数
         * 00.000 -> 取两位整数和三位小数
         * # -> 取所有整数部分
         * #.##% -> 以百分比方式计数，并取两位小数
         * #.#####E0 -> 显示为科学计数法，并取五位小数
         * ,### -> 每三位以逗号进行分隔，例如：299,792,458
         * 光速大小为每秒,###米 -> 将格式嵌入文本
         */
        /*long c=299792458;//光速
        String format = NumberUtil.decimalFormat(",###", c);//299,792,458
        System.out.println(format);*/

        /**
         * NumberUtil.isNumber 是否为数字
         * NumberUtil.isInteger 是否为整数
         * NumberUtil.isDouble 是否为浮点数
         * NumberUtil.isPrimes 是否为质数
         */

        /**
         * NumberUtil.generateRandomNumber 生成不重复随机数 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组。
         * NumberUtil.generateBySet 生成不重复随机数 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组。
         */
        /*int[] ints = NumberUtil.generateRandomNumber(0, 100000000, 6);
        System.out.println(ArrayUtil.toString(ints));//[17200549, 9090350, 35927713, 66671494, 72328793, 20283921]
        Integer[] integers = NumberUtil.generateBySet(0, 100000, 6);
        System.out.println(ArrayUtil.toString(integers));//[12849, 77696, 88711, 87259, 50078, 750]*/

        /**
         * NumberUtil.factorial 阶乘
         * NumberUtil.sqrt 平方根
         * NumberUtil.divisor 最大公约数
         * NumberUtil.multiple 最小公倍数
         * NumberUtil.getBinaryStr 获得数字对应的二进制字符串
         * NumberUtil.binaryToInt 二进制转int
         * NumberUtil.binaryToLong 二进制转long
         * NumberUtil.compare 比较两个值的大小
         * NumberUtil.toStr 数字转字符串，自动并去除尾小数点儿后多余的0
         */
        /*System.out.println(NumberUtil.toStr(1800.00200));*/

        /*String ID_18 = "321083197812162119";
        String ID_15 = "150102880730303";

//是否有效
        boolean valid = IdcardUtil.isValidCard(ID_18);
        boolean valid15 = IdcardUtil.isValidCard(ID_15);
        System.out.println(valid+"======="+valid15);

//转换
        String convert15To18 = IdcardUtil.convert15To18(ID_15);
        System.out.println(convert15To18);

//年龄
        DateTime date = DateUtil.parse("2017-04-10");

        int age = IdcardUtil.getAgeByIdCard(ID_18, date);
        System.out.println(age);

        int age2 = IdcardUtil.getAgeByIdCard(ID_15, date);
        System.out.println(age2);

//生日
        String birth = IdcardUtil.getBirthByIdCard(ID_18);
        System.out.println(birth);

        String birth2 = IdcardUtil.getBirthByIdCard(ID_15);
        System.out.println(birth2);

//省份
        String province = IdcardUtil.getProvinceByIdCard(ID_18);
        System.out.println(province);

        String province2 = IdcardUtil.getProvinceByIdCard(ID_15);
        System.out.println(province2);*/


        /**
         * 图片工具-ImageUtil
         * 介绍
         * 针对awt中图片处理进行封装，这些封装包括：缩放、裁剪、转为黑白、加水印等操作。
         *
         * 方法介绍
         * scale 缩放图片，提供两种重载方法：其中一个是按照长宽缩放，另一种是按照比例缩放。
         * cut 剪裁图片
         * cutByRowsAndCols 按照行列剪裁（将图片分为20行和20列）
         * convert 图片类型转换，支持GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG等
         * gray 彩色转为黑白
         * pressText 添加文字水印
         * pressImage 添加图片水印
         * rotate 旋转图片
         * flip 水平翻转图片
         */

        //生成的UUID是带-的字符串，类似于：a5c8a5e8-df2b-4706-bea4-08d0939410e3
        /*String uuid = IdUtil.randomUUID();
        System.out.println(uuid);

//生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();
        System.out.println(simpleUUID);*/

        //参数1为终端ID
        //参数2为数据中心ID
        /*Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println(id);*/

        /**
         * Dict继承hashmap
         */
        /*Dict dict = Dict.create()
                .set("key1", 1)//int
                .set("key2", 1000L)//long
                .set("key3", DateTime.now());//Date
        System.out.println(JSONUtil.parseObj(dict));*/


        /*Map<String, Object> m = new HashMap1<String, Object>() {{
            put("k1", "v1");
            put("k2", "v2");
            put("k3", "v3");
        }};
        Map<String,Object> m1 = new HashMap1<String,Object>(){
            {
                put("k4","v4");
                put("k5","v5");
                put("k6","v6");
                put("k7","v7");
            }
        };
        System.out.println(JSONUtil.parseObj(m1));

        Map<String,Map<String,String>> m2 = new LinkedHashMap<String, Map<String, String>>(){{
            put("aa",new HashMap1<String, String>(){
                {
                    put("bb","cc");put("vv","cc");put("ff","cc");put("rr","cc");
                }

            });
            put("bb",new HashMap1<String, String>(){{
                put("ff","ffgghh");
            }});
        }};
        System.out.println(JSONUtil.parseObj(m2));
        Console.log("{}",m2);*/
        /*String[] inc = {"k1", "k3"};//需要的key
        final List<String> incList = Arrays.asList(inc);
        m = CollectionUtil.filter(m, new Editor<Map.Entry<String, Object>>() {
            @Override
            public Map.Entry<String, Object> edit(Map.Entry<String, Object> stringObjectEntry) {
                if (incList.contains(stringObjectEntry.getKey())) {
                    return stringObjectEntry;
                }
                return null;
            }
        });
        Console.log("{}", m);*/

        // 自动选择日志实现
        /*Log log = LogFactory.get();
        log.debug("This is {} log", "default");
        Console.log("----------------------------------------------------------------------");

//自定义日志实现为Apache Commons Logging
        LogFactory.setCurrentLogFactory(new ApacheCommonsLogFactory());
        log.debug("This is {} log", "custom apache commons logging");
        Console.log("----------------------------------------------------------------------");

//自定义日志实现为JDK Logging
        LogFactory.setCurrentLogFactory(new JdkLogFactory());
        log.info("This is {} log", "custom jdk logging");
        Console.log("----------------------------------------------------------------------");

//自定义日志实现为Console Logging
        LogFactory.setCurrentLogFactory(new ConsoleLogFactory());
        log.info("This is {} log", "custom Console");
        Console.log("----------------------------------------------------------------------");*/

        /*Log log = LogFactory.get();

        log.debug("This is {} log", Level.DEBUG);
        log.info("This is {} log", Level.INFO);
        log.warn("This is {} log", Level.WARN);

        Exception e = new Exception("test Exception");
        log.error(e, "This is {} log", Level.ERROR);*/

        //自定义日志实现为Apache Commons Logging
       /* LogFactory.setCurrentLogFactory(new ApacheCommonsLogFactory());

//自定义日志实现为JDK Logging
        LogFactory.setCurrentLogFactory(new JdkLogFactory());

//自定义日志实现为Console Logging
        LogFactory.setCurrentLogFactory(new ConsoleLogFactory());

        log.log(Level.DEBUG,"This is {} log","aaaaaaaaaaaaa");

        /*StaticLog.info("This is static {} log.", "INFO");*/


       /** FIFOCache
                介绍
        FIFO(first in first out) 先进先出策略。元素不停的加入缓存直到缓存满为止，当缓存满时，清理过期缓存对象，清理后依旧满则删除先入的缓存（链表首部对象）。

        优点：简单快速
        缺点：不灵活，不能保证最常用的对象总是被保留*/


        /*Cache<String,String> fifoCache = CacheUtil.newFIFOCache(3);

//加入元素，每个元素可以设置其过期时长，DateUnit.SECOND.getMillis()代表每秒对应的毫秒数，在此为3秒
        fifoCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
        fifoCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
        fifoCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);

//由于缓存容量只有3，当加入第四个元素的时候，根据FIFO规则，最先放入的对象将被移除
        fifoCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);

//value1为null
        String value1 = fifoCache.get("key1");
        System.out.println(value1);
        System.out.println(fifoCache);*/

        /**LFUCache
                介绍
        LFU(least frequently used) 最少使用率策略。根据使用次数来判定对象是否被持续缓存（使用率是通过访问次数计算），当缓存满时清理过期对象，清理后依旧满的情况下清除最少访问（访问计数最小）的对象并将其他对象的访问数减去这个最小访问数，以便新对象进入后可以公平计数。*/


        /*Cache<String, String> lfuCache = CacheUtil.newLFUCache(3);
//通过实例化对象创建
//LFUCache<String, String> lfuCache = new LFUCache<String, String>(3);

        lfuCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
        lfuCache.get("key1");//使用次数+1
        lfuCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
        lfuCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);
        lfuCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);

//由于缓存容量只有3，当加入第四个元素的时候，根据LRU规则，最少使用的将被移除（2,3被移除）
        String value2 = lfuCache.get("key2");//null
        String value3 = lfuCache.get("key3");//null
        System.out.println(value2);
        System.out.println(value3);*/

        /**LRUCache
                介绍
        LRU (least recently used)最近最久未使用缓存。根据使用时间来判定对象是否被持续缓存，当对象被访问时放入缓存，当缓存满了，最久未被使用的对象将被移除。此缓存基于LinkedHashMap，因此当被缓存的对象每被访问一次，这个对象的key就到链表头部。这个算法简单并且非常快，他比FIFO有一个显著优势是经常使用的对象不太可能被移除缓存。缺点是当缓存满时，不能被很快的访问。*/


        /*Cache<String, String> lruCache = CacheUtil.newLRUCache(3);
//通过实例化对象创建
//LRUCache<String, String> lruCache = new LRUCache<String, String>(3);
        lruCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
        lruCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
        lruCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);
        lruCache.get("key1");//使用时间推近
        lruCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);

//由于缓存容量只有3，当加入第四个元素的时候，根据LRU规则，最少使用的将被移除（2被移除）
        String value2 = lruCache.get("key2");//null
        System.out.println(value2);*/

        /**
         * TimedCache
         *                 介绍
         *         定时缓存，对被缓存的对象定义一个过期时间，当对象超过过期时间会被清理。此缓存没有容量限制，对象只有在过期后才会被移除。
         *
         *         使用
         */


//创建缓存，默认4毫秒过期
        /*TimedCache<String, String> timedCache = CacheUtil.newTimedCache(4);
//实例化创建
//TimedCache<String, String> timedCache = new TimedCache<String, String>(4);

        timedCache.put("key1", "value1", 1);//1毫秒过期
        timedCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 5);//5s
        timedCache.put("key3", "value3");//默认过期(4毫秒)

//启动定时任务，每5毫秒秒检查一次过期
        timedCache.schedulePrune(5);
//等待5毫秒
        ThreadUtil.sleep(5);

//5毫秒后由于value2设置了5秒过期，因此只有value2被保留下来
        String value1 = timedCache.get("key1");//null
        String value2 = timedCache.get("key2");//value2

//5毫秒后，由于设置了默认过期，key3只被保留4毫秒，因此为null
        String value3 = timedCache.get("key3");//null

//取消定时清理
        timedCache.cancelPruneSchedule();*/

        /*WeakCache
                介绍
        弱引用缓存。对于一个给定的键，其映射的存在并不阻止垃圾回收器对该键的丢弃，这就使该键成为可终止的，被终止，然后被回收。丢弃某个键时，其条目从映射中有效地移除。该类使用了WeakHashMap做为其实现，缓存的清理依赖于JVM的垃圾回收。

        使用
        与TimedCache使用方法一致：WeakCache也可以像TimedCache一样设置定时清理时间，同时具备垃圾回收清理。*/

        //WeakCache<String, String> weakCache = CacheUtil.newWeakCache(DateUnit.SECOND.getMillis() * 3);


        /*FileCache
                介绍
        FileCache主要是将小文件以byte[]的形式缓存到内容中，减少文件的访问，以解决频繁读取文件引起的性能问题。

        实现
                LFUFileCache
        LRUFileCache
                使用*/
//参数1：容量，能容纳的byte数
//参数2：最大文件大小，byte数，决定能缓存至少多少文件，大于这个值不被缓存直接读取
//参数3：超时。毫秒
        /*cn.hutool.cache.file.LFUFileCache cache = new LFUFileCache(1000, 500, 2000);
        byte[] bytes = cache.getFileBytes("d:/a.jpg");*/


        /**
         * 同样，JSONUtil还可以支持以下对象转为JSONObject对象：
         *
         * String对象
         * Java Bean对象
         * Map对象
         * XML字符串（使用JSONUtil.parseFromXml方法）
         * ResourceBundle(使用JSONUtil.parseFromResourceBundle)
         * JSONUtil还提供了JSONObject对象转换为其它对象的方法：
         *
         * toJsonStr 转换为JSON字符串
         * toXmlStr 转换为XML字符串
         * toBean 转换为JavaBean
         */
        /*String jsonStr = "{\"b\":\"value2\",\"c\":\"value3\",\"a\":\"value1\"}";
//方法一：使用工具类转换
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
//方法二：new的方式转换
        JSONObject jsonObject2 = new JSONObject(jsonStr);

//JSON对象转字符串
        jsonObject.toString();
        System.out.println(jsonObject.toString());*/

        /*String jsonStr = "[\"value1\", \"value2\", \"value3\"]";
        JSONArray array = JSONUtil.parseArray(jsonStr);
        System.out.println(array);*/


        /**
         * 全局定时任务-CronUtil
         * 介绍
         * CronUtil通过一个全局的定时任务配置文件，实现统一的定时任务调度。
         *
         * 使用
         * 1、配置文件
         * 对于Maven项目，首先在src/main/resources/config下放入cron.setting文件（默认是这个路径的这个文件），然后在文件中放入定时规则，规则如下：
         *
         * # 我是注释
         * [com.company.aaa.job]
         * TestJob.run = *//**10 * * * *
         *TestJob2.run = *//**10 * * * *
         *中括号表示分组，也表示需要执行的类或对象方法所在包的名字，这种写法有利于区分不同业务的定时任务。
         *
         *TestJob.run表示需要执行的类名和方法名（通过反射调用），*//**10 * * * *表示定时任务表达式，此处表示每10分钟执行一次，以上配置等同于：
         *
         *com.company.aaa.job.TestJob.run = *//**10 * * * *
         *com.company.aaa.job.TestJob2.run = *//**10 * * * *
         *提示
                * 关于表达式语法，见：http:
//www.cnblogs.com/peida/archive/2013/01/08/2850483.html
         *
         *2、启动
                * CronUtil.start();
         *如果想让执行的作业同定时任务线程同时结束，可以将定时任务设为守护线程，需要注意的是，此模式下会在调用stop时立即结束所有作业线程，请确保你的作业可以被中断：
         *
         * //使用deamon模式，
         *CronUtil.start(true);
         *3、关闭
                * CronUtil.stop();
         */
        //CronUtil.schedule("*/2 * * * * *", new Task() {
         //   @Override
         //   public void execute() {
         //       Console.log("Task excuted.");
         //   }
       // });

// 支持秒级别定时任务
     //   CronUtil.setMatchSecond(true);
       // CronUtil.start();



        //MailUtil.send("630185513@qq.com", "测试", "邮件来自Hutool测试", false);

        /*群发邮件，可选HTML或普通文本，可选多个附件：*/
        /*ArrayList<String> tos = CollUtil.newArrayList(
                "630185513@qq.com",
                "630185513@qq.com",
                "630185513@qq.com",
                "630185513@qq.com");

        MailUtil.send(tos, "测试", "邮件来自Hutool群发测试", false);*/

        /*发送HTML格式的邮件并附带附件，最后一个参数可选是否添加多个附件：*/
        //MailUtil.send("hutool@foxmail.com", "测试", "<h1>邮件来自Hutool测试</h1>", true, FileUtil.file("d:/aaa.xml"));


        /**
         * ServletUtil
         *
         * getParamMap 获得所有请求参数
         * fillBean 将请求参数转为Bean
         * getClientIP 获取客户端IP，支持从Nginx头部信息获取，也可以自定义头部信息获取位置
         * getHeader、getHeaderIgnoreCase 获得请求header中的信息
         * isIE 客户浏览器是否为IE
         * isMultipart 是否为Multipart类型表单，此类型表单用于文件上传
         * getCookie 获得指定的Cookie
         * readCookieMap 将cookie封装到Map里面
         * addCookie 设定返回给客户端的Cookie
         * write 返回数据给客户端
         * setHeader 设置响应的Header
         */


// 生成指定url对应的二维码到文件，宽和高都是300像素
        //QrCodeUtil.generate("http://hutool.cn/", 300, 300, FileUtil.file("C:\\Users\\super\\Desktop\\qq.jpg"));


        /*QrConfig config = new QrConfig(300, 300);
// 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
// 设置前景色，既二维码颜色（青色）
        config.setForeColor(Color.CYAN.getRGB());
// 设置背景色（灰色）
        config.setBackColor(Color.GRAY.getRGB());
// 生成二维码到文件，也可以到流
        QrCodeUtil.generate("http://hutool.cn/", config, FileUtil.file("C:\\\\Users\\\\super\\\\Desktop\\\\qq.jpg"));*/

        /*识别二维码*/
// decode -> "http://hutool.cn/"
        /*String decode = QrCodeUtil.decode(FileUtil.file("C:\\Users\\super\\Desktop\\qq.jpg"));
        System.out.println(decode);*/


        /*从字符串模板渲染内容*/
//自动根据用户引入的模板引擎库的jar来自动选择使用的引擎
//TemplateConfig为模板引擎的选项，可选内容有字符编码、模板路径、模板加载方式等，默认通过模板字符串渲染
        /*TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());

//假设我们引入的是Beetl引擎，则：
        Template template = engine.getTemplate("Hello ${name}");
//Dict本质上为Map，此处可用Map
        String render = template.render(Dict.create().set("name", "Hutool"));
//输出：Hello Hutool
        System.out.println(render);*/


        /*从classpath查找模板渲染
        只需修改TemplateConfig配置文件内容即可更换（这里以Velocity为例）：*/

       /* TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("templates/velocity_test.vtl");
        String render = template.render(Dict.create().set("name", "Hutool"));*/

    }
}
