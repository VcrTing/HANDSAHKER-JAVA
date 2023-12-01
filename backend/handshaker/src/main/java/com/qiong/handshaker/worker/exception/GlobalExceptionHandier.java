package com.qiong.handshaker.worker.exception;

import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.exception.vaiid.QValidError;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.tool.result.QResponseTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandier {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandier.class);

    /**
    * 自定义 运行时 异常
    * @params
    * @return
    */
    @ExceptionHandler(QLogicException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @QResponseAdvice
    public QResponse<QLogicException> handierRunTime(QLogicException re) {
        logger.info("RUNTIME 我在调试的 错误 = " + re.getLocalizedMessage());
        return QResponseTool.genBad(re.getLocalizedMessage(), re);
    }

    /**
    * Servlet 相关异常
    * @params
    * @return
    */
    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public QResponse<ServletException> handierRequest(ServletException srb) {
        logger.info("SERVLET EXCEPTION = " + srb.getLocalizedMessage());
        return QResponseTool.genBad(srb.getLocalizedMessage(), srb);
    }

    /**
    * spring 验证字段 异常
    * @params
    * @return
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public QResponse<List<QValidError>> handierValid(MethodArgumentNotValidException ve) {
        logger.info("字段验证错误 = " + ve.getLocalizedMessage());
        return QResponseTool.genBad("参数校验错误", genValidErr(ve.getBindingResult().getFieldErrors()));
    }

    private List<QValidError> genValidErr(List<FieldError> errors) {
        List<QValidError> ves = new ArrayList<>();
        errors.forEach(e-> ves.add(new QValidError(e.getField(), e.getDefaultMessage())));
        return ves;
    }
}
