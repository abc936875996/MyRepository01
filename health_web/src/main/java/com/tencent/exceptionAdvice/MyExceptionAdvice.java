package com.tencent.exceptionAdvice;

import com.tencent.exception.CheckItemUsedException;
import com.tencent.exception.OrderSettingException;
import com.tencent.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * log日志：
 * info：记录日志的内容，对重要业务记录流程。
 * debug：记录重要数据的key和value。
 * error：记录日常信息，system.out，e.printStackTrace()
 * 注意：工作中代码中尽量不要使用e.printStackTrace()，因为可能会将内存占满，
 * 程序崩溃；并且日志只会打印到控制台，不会导出到某个文件所以建议使用 slf4j或者 log4j的 logger.error(）方法
 * </p>
 *
 * @author PR
 * @package com.tencent.exceptionAdvice
 * @date 2021/1/7 16:54
 */
@RestControllerAdvice
public class MyExceptionAdvice {
    /**
     * 使用slf4j定位异常代码位置
     */
    private static final Logger log = LoggerFactory.getLogger(MyExceptionAdvice.class);

    /**
     * 检查项被使用异常
     */
    @ExceptionHandler(CheckItemUsedException.class)
    public Result handleCheckItemUsedException(CheckItemUsedException e) {
        return new Result(false, e.getMessage());
    }

    /**
     * 预约设置异常
     */
    @ExceptionHandler(OrderSettingException.class)
    public Result handleOrderSettingException(OrderSettingException e) {
        return new Result(false, e.getMessage());
    }

    /**
     * 未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        //记录异常信息
        log.error("发生未知异常", e);
        //友好提示用户
        return new Result(false, "发生未知异常,请联系管理员");
    }
}
