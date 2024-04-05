package com.practice.common.exception;

import com.practice.common.lang.Result;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;

/**
 * 全局异常处理
 */
@Slf4j // log是从这里拿出来的
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕捉shiro的异常
     * 比如没有权限，用户登录异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // UNAUTHORIZED:未经授权的
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        return Result.error(401, e.getMessage(), null);
    }

    /**
     * 处理Assert的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) throws IOException {
        log.error("Assert异常:-------------->{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理前端请求不传必传参数的错误
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected Result handler(HttpMessageNotReadableException e) {
        log.error("运行时异常:-------------->", e);
        return Result.error("缺少参数");
    }

    /**
     * @Validated 校验错误异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) throws IOException {
        log.error("运行时异常:-------------->", e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.error(objectError.getDefaultMessage());
    }

    /**
     * 你redis没打开!!
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = JedisConnectionException.class)
    public Result handler(JedisConnectionException e) {
        log.error("你redis没打开!!");
        return Result.error(e.getMessage());
    }

    /**
     * 捕捉其他异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) throws IOException {
        log.error("运行时异常:-------------->", e);
        return Result.error(e.getMessage());
    }
}
