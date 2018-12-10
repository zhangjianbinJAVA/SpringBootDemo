package com.dudu.custweb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(ConstraintViolationException exception, HttpServletRequest request) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuffer errorInfo = new StringBuffer();
        for (ConstraintViolation<?> item : violations) {
            /**打印验证不通过的信息*/
            errorInfo.append(item.getMessage());
            errorInfo.append(",");
            log.error("Item:" + item.getPropertyPath().toString() + "  message:" + item.getMessage());
        }
        log.error("{}接口参数验证失败，内容如下：{}", request.getRequestURI(), errorInfo.toString());
        return "您的请求失败，参数验证失败，失败信息如下：" + errorInfo.toString();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handle(MethodArgumentNotValidException exception, HttpServletRequest request) {
        StringBuffer errorInfo = new StringBuffer();
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        for (int i = 0; i < errors.size(); i++) {
            errorInfo.append(errors.get(i).getDefaultMessage() + ",");
        }
        log.error("{},接口参数验证失败：{}", request, errorInfo.toString());
        return "您的请求失败，参数验证失败，失败信息如下:" + errorInfo.toString();
    }


}