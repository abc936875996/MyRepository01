package com.tencent.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.service.SetmealService;
import com.tencent.utils.QiNiuUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.jobs
 * @date 2021/1/8 21:29
 */
@Component("cleanImgJob")
public class CleanImgJob {
    @Reference
    private SetmealService setmealService;

//    @Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void cleanImg() {
        //查询db获取图片名集合
        List<String> imgList = setmealService.findImgList();
        //使用七牛云工具类获取文件名集合
        List<String> listFile = QiNiuUtils.listFile();
        //只清理图片文件
        listFile.removeIf(s -> !s.endsWith(".jpg"));
        //db-七牛云得到要删除的文件名列表
        listFile.removeAll(imgList);
        //调用七牛云工具删除文件
        QiNiuUtils.removeFiles(listFile.toArray(new String[]{}));
        System.out.println("定时清理成功！！！");
    }
}
