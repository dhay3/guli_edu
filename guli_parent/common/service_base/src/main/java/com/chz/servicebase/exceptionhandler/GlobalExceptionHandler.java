package com.chz.servicebase.exceptionhandler;

import com.chz.exception.CusException;
import com.chz.response.ResponseBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局处理异常
 * 将异常信息加入到日志文件中
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 错误也返回ResponseBo统一处理
     */
    @ExceptionHandler(CusException.class)
    public ResponseBo cusErrorHandler(CusException e) {
        log.error("{}", "throw CusException");
        e.printStackTrace();
//        log.error(ExceptionUtil.getMessage(e));
        return ResponseBo.error()
                .message(e.getMessage())
                .code(e.getErrorCode());
    }

    /**
     * 没有可以处理异常的ExceptionHandler经过该处理器
     */
    @ExceptionHandler
    public ResponseBo errorHandler(Exception e) {
        log.error("{}", "没有匹配的处理器,调用全局异常处理器");
        e.printStackTrace();
//        log.error(ExceptionUtil.getMessage(e));
        return ResponseBo.error()
                .message(e.getMessage());
    }
}
