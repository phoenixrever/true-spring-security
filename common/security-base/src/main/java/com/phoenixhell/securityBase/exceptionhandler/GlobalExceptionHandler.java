//package com.phoenixhell.securityBase.exceptionhandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.ExceptionUtil;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.Arrays;
//
///**
// * @author phoenixhell
// * @create 2021/2/26 0026-上午 10:53
// * 统一异常处理类
// */
//
//@ControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//    //捕获的异常类型 下面为全部异常
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public CommonResult globalError(Exception e){
//        e.printStackTrace();
//        return CommonResult.error().data("全局错误",e.getMessage());
//    }
//    @ExceptionHandler(value = MyException.class)
//    @ResponseBody
//    public CommonResult myException(MyException e){
//        log.error(ExceptionUtil.getMessage(e));
//        return CommonResult.error().data("customMessage", Arrays.asList(e.getCode(), e.getMsg()));
//    }
//}
