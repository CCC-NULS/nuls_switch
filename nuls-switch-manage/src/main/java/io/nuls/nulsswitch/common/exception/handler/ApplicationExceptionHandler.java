package io.nuls.nulsswitch.common.exception.handler;

import io.nuls.nulsswitch.api.exception.IFastApiException;
import io.nuls.nulsswitch.common.exception.IFastException;
import io.nuls.nulsswitch.common.type.EnumErrorCode;
import io.nuls.nulsswitch.common.utils.HttpContextUtils;
import io.nuls.nulsswitch.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 */
@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    public final static String ERROR_DEFAULT_PAGE = "error/error";

    /**
     * 参数校验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> illegalArgumentException(IllegalArgumentException e) {
        log.info("全局异常处理:IllegalArgumentException[{}]:{}", e.getClass(), e.getMessage());
        return Result.build(EnumErrorCode.illegalArgument.getCode(), e.getMessage());
    }

    /**
     * API异常
     */
    @ExceptionHandler(IFastApiException.class)
    public Result<String> handleIFastAPIException(IFastApiException e) {
        log.info("全局异常处理:IFastApiException[{}]:{}", e.getClass(), e.getMessage());
        return Result.buildByErrorCode(e.getMessage());
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(IFastException.class)
    public Object handleIFastException(IFastException e) {
        log.info("全局异常处理:IFastException[{}]:{}", e.getClass(), e.getMessage());
        if (!HttpContextUtils.isAjax()) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName(ERROR_DEFAULT_PAGE);
            return mv;
        } else {
            return Result.buildByErrorCode(e.getMessage());
        }
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException e) {
        log.info("全局异常处理:DuplicateKeyException[{}]:{}", e.getClass(), e.getMessage());
        return Result.build(EnumErrorCode.duplicateKeyExist.getCode(), EnumErrorCode.duplicateKeyExist.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> noHandlerFoundException(NoHandlerFoundException e) {
        log.info("全局异常处理:NoHandlerFoundException[{}]:{}", e.getClass(), e.getMessage());
        return Result.build(EnumErrorCode.pageNotFound.getCode(), EnumErrorCode.pageNotFound.getMsg());
    }

    @ExceptionHandler(ShiroException.class)
    public Result<String> handleAuthorizationException(ShiroException e) {
        log.info("全局异常处理:ShiroException[{}][{}]:{}", e.getClass(), e.getClass(), e.getMessage());
        if (e instanceof IncorrectCredentialsException) {
            return Result.build(EnumErrorCode.apiAuthorizationFailed.getCode(), EnumErrorCode.apiAuthorizationFailed.getMsg());
        } else if (e instanceof ExpiredCredentialsException) {
            return Result.build(EnumErrorCode.apiAuthorizationExpired.getCode(), EnumErrorCode.apiAuthorizationExpired.getMsg());
        } else if (e instanceof AuthenticationException) {
            return Result.build(EnumErrorCode.userLoginFail.getCode(), EnumErrorCode.userLoginFail.getMsg());

        }
        return Result.build(EnumErrorCode.notAuthorization.getCode(), EnumErrorCode.notAuthorization.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        log.info("全局异常处理:Exception[{}]:{}", e.getClass(), e.getMessage());
        e.printStackTrace();
        if (!HttpContextUtils.isAjax()) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName(ERROR_DEFAULT_PAGE);
            return mv;
        } else {
            return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
        }
    }
}
