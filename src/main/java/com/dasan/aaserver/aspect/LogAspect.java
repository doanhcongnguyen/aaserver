package com.dasan.aaserver.aspect;

import com.dasan.aaserver.exception.BadRequestException;
import com.dasan.aaserver.utils.CommonUtils;
import com.dasan.aaserver.utils.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.dasan.aaserver.aspect.Log)")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long currentTime = System.currentTimeMillis();
        RequestDto requestDto = this.createRequestDto(joinPoint);
        Object result = joinPoint.proceed();
        this.updateRequestDto(requestDto, currentTime, result);
        log.info(requestDto.toString());
        return result;
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        long currentTime = System.currentTimeMillis();
        RequestDto requestDto = this.createRequestDto(joinPoint);
        this.updateRequestDto(requestDto, currentTime, e);
        log.info(requestDto.toString());
    }

    private void updateRequestDto(RequestDto requestDto, long currentTime, Object result) {
        this.setTime(requestDto, currentTime);
        this.setStatus(requestDto, result);
    }

    private void setStatus(RequestDto requestDto, Object result) {
        if (result instanceof ResponseEntity) {
            ResponseEntity response = (ResponseEntity) result;
            requestDto.setStatus(response.getStatusCodeValue());
        } else if (result instanceof BadRequestException) {
            BadRequestException response = (BadRequestException) result;
            requestDto.setStatus(response.getStatus());
        }
    }

    private void setTime(RequestDto requestDto, long currentTime) {
        requestDto.setTime(String.format("%s ms", System.currentTimeMillis() - currentTime));
    }

    private RequestDto createRequestDto(JoinPoint joinPoint) {
        RequestDto requestDto = new RequestDto();
        requestDto.setRequestId(UUID.randomUUID().toString());
        this.setApi(joinPoint, requestDto);
        this.setIp(requestDto);
        return requestDto;
    }

    private void setIp(RequestDto requestDto) {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        requestDto.setIp(CommonUtils.getIP(request));
    }

    private void setApi(JoinPoint joinPoint, RequestDto requestDto) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        Log aspectLog = signatureMethod.getAnnotation(Log.class);
        requestDto.setApi(aspectLog.value());
    }
}
