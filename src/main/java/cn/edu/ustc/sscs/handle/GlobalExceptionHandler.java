package cn.edu.ustc.sscs.handle;

import cn.edu.ustc.sscs.common.exception.AppException;
import cn.edu.ustc.sscs.domain.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error(e.getMessage());
        if(e instanceof AppException) {
            return Result.error(e.getMessage());
        }
        return Result.error("服务异常");
    }
}
