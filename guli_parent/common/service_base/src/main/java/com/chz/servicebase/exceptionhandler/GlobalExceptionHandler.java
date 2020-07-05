package com.chz.servicebase.exceptionhandler;

import com.chz.utils.CusException;
import com.chz.utils.ExceptionUtil;
import com.chz.utils.ResponseBo;
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
    public ResponseBo error(CusException e) {
        e.printStackTrace();
        log.error( ExceptionUtil.getMessage(e));
        return ResponseBo.error()
                .message(e.getMessage())
                .code(e.getErrorCode());
    }
}
