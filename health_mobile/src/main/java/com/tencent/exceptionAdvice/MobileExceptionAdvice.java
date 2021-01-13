package com.tencent.exceptionAdvice;

import com.tencent.constant.MessageConstant;
import com.tencent.exception.CannotOrderException;
import com.tencent.exception.OrderFullException;
import com.tencent.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author PR
 * @package com.tencent.exceptionAdvice
 * @date 2021/1/12 16:04
 */
@RestControllerAdvice
public class MobileExceptionAdvice {
    /**
     * 当日预约已满异常
     */
    @ExceptionHandler(OrderFullException.class)
    public Result handleOrderFullException(OrderFullException e) {
        return new Result(false, e.getMessage());
    }

    /**
     *当日不能预约异常
     */
    @ExceptionHandler(CannotOrderException.class)
    public Result handleCannotOrderException(CannotOrderException e) {
        return new Result(false, e.getMessage());
    }
}
