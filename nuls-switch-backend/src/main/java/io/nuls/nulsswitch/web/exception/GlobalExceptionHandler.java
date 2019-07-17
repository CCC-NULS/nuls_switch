
package io.nuls.nulsswitch.web.exception;


import io.nuls.nulsswitch.web.eums.ErrorCodeEnum;
import io.nuls.nulsswitch.wrapper.WrapMapper;
import io.nuls.nulsswitch.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

/**
 * 全局的的异常拦截器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数非法异常.
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper illegalArgumentException(IllegalArgumentException e) {
        log.info("controller throws IllegalArgumentException!", e);
        return WrapMapper.wrap(ErrorCodeEnum.GL99990100.code(), e.getMessage());
    }

    /**
     * 参数格式错误
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper illegalArgumentException(BindException e) {
        log.info("controller throws illegalArgumentException!", e);
        return WrapMapper.wrap(ErrorCodeEnum.GL99990110.code(), ErrorCodeEnum.GL99990110.msg());
    }

    /**
     * 业务异常.
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper businessException(BusinessException e) {
        log.info("controller throws BusinessException!", e);
        return WrapMapper.wrap(e.getCode() == 0 ? Wrapper.ERROR_CODE : e.getCode(), e.getMessage());
    }


    /**
     * 全局异常.
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper exception(Exception e) {
        log.error("controller throws exception!", e);
        return WrapMapper.wrap(ErrorCodeEnum.GL99990500.code(), ErrorCodeEnum.GL99990500.msg());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public Wrapper multipartException(Throwable t) {
        log.error("上传文件, 出现错误={}", t.getMessage(), t);
        return WrapMapper.wrap(Wrapper.ERROR_CODE, "文件超过限制");
    }
}
