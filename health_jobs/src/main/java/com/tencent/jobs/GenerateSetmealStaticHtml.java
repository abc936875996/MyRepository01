package com.tencent.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.pojo.Setmeal;
import com.tencent.service.SetmealService;
import com.tencent.utils.QiNiuUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

/**
 * @author PR
 * @package com.tencent.jobs
 * @date 2021/1/11 10:28
 */
@Component("generateSetmealStaticHtml")
public class GenerateSetmealStaticHtml {
    Logger logger = LoggerFactory.getLogger(GenerateSetmealStaticHtml.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 1.引入freemarkerConfiguration对象
     */
    @Autowired
    private Configuration configuration;

    @Reference
    private SetmealService setmealService;

    @Value("${out_put_path}")
    private String OUT_PUT_PATH;

    @PostConstruct
    public void init() throws IOException {
        //2.设置使用默认编码utf-8
        configuration.setDefaultEncoding("utf-8");
        //3.设置模板存放目录
        configuration.setClassForTemplateLoading(GenerateSetmealStaticHtml.class, "/ftl");
    }

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void generateHtml() throws Exception {
        //4.读取redis的ZSet获取套餐操作集合
        Jedis jedis = jedisPool.getResource();

        String key = "setmeal:static:html";
        Set<String> setmealSet = jedis.zrange(key, 0, -1);

        //5.判断集合非空
        if (!CollectionUtils.isEmpty(setmealSet)) {
            //6.遍历集合
            for (String setmeal : setmealSet) {
                String[] setmealIdsArr = setmeal.split("\\|");
                //套餐ID
                int setmealId = Integer.parseInt(setmealIdsArr[0]);
                //操作符
                int operation = Integer.parseInt(setmealIdsArr[1]);
                //存储的时间戳
                Long timestamp = Long.parseLong(setmealIdsArr[2]);
                //6.根据操作符判断
                if (operation == 0) {
                    //7.删除套餐详情页
                    deleteSetmealDetailHtml(setmealId);
                    logger.debug("删除详情页面" + setmealId + "，时间:" + timestamp);
                } else if (operation == 1) {
                    //7.生成套餐详情页
                    generateSetmealDetailHtml(setmealId);
                    logger.debug("生成详情页面" + setmealId + "，时间:" + timestamp);
                } else {
                    logger.error("操作符错误！");
                }
                //8.从redis中删除处理过的套餐
                jedis.zrem(key, setmeal);
            }
            //9.生成静态列表页
            generateSetmealHtml();
            logger.debug("生成静态列表页...");
        }
    }

    /**
     * 生成套餐详情页
     */
    private void generateSetmealDetailHtml(int setmealId) throws Exception {
        //1.获取模板对象
        String templateName = "setmeal_detail.ftl";
        //2.获取需要填充的数据
        Setmeal setmeal = setmealService.findSetmealDetailById(setmealId);
        //2.1设置图片
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        //3.构建数据Map
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("setmeal", setmeal);
        //4.文件名
        String filename = "/mobile_setmeal_detail_" + setmealId + "_static.html";
        //5.调用方法生成页面
        this.generateStaticHtml(templateName, filename, dataMap);
    }

    /**
     * 删除套餐详情页
     */
    private void deleteSetmealDetailHtml(int setmealId) {
        String filename = "mobile_setmeal_detail_" + setmealId + "_static.html";
        File file = new File(OUT_PUT_PATH + filename);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 生成套餐列表页
     */
    private void generateSetmealHtml() throws Exception {
        //0.模板名
        String templateName = "setmeal.ftl";
        //1.获取需要填充的数据
        List<Setmeal> setmealList = setmealService.findSetmealList();
        //1.1 设置图片
        setmealList.forEach(setmeal -> setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg()));
        //2.构建数据Map
        Map<String, Object> dataMap = new HashMap<>();
        //3.Key要参考模板中的setmealList as setmeal
        dataMap.put("setmealList", setmealList);
        //4.文件名
        String filename = "/mobile_setmeal_static.html";
        //5.调用方法生成页面
        this.generateStaticHtml(templateName, filename, dataMap);
    }

    public void generateStaticHtml(String templateName, String filename, Map<String, Object> dataMap) throws Exception {
        //1.获取模板对象
        Template template = configuration.getTemplate(templateName);

        //2.获取字符输出流:OutputStreamWriter(将字节输出流转换为字符输出流)
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(OUT_PUT_PATH + filename), "utf-8"));
        //3.将数据填充进模板
        template.process(dataMap, bw);
        //4.关流
        bw.flush();
        bw.close();
    }
}
