package com.bit.shoppingmall.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Order(2)
@Slf4j
public class LoggingAspect {
    /**
     * request 에 담긴 정보를 JSONObject 형태로 반환한다.
     *
     * @param request
     * @return
     */
    private static JSONObject getParams(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }

    @Pointcut("execution(* com.bit.shoppingmall.web..*Controller.*(..))") // 이런 패턴이 실행될 경우 수행
    public void loggerPointCut() {
    }

    @Around("loggerPointCut()")
    public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object result = proceedingJoinPoint.proceed();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); // request 정보를 가져온다.

            String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();

            Map<String, Object> params = new HashMap<>();

            try {
                params.put("controller", controllerName);
                params.put("method", methodName);
                if (methodName.equals("login")) {
                    params.put("params", "{masked}");
                } else {
                    params.put("params", getParams(request));
                }
                params.put("log_time", new Date());
                params.put("request_uri", request.getRequestURI());
                params.put("http_method", request.getMethod());
            } catch (Exception e) {
                log.error("LoggerAspect error", e);
            }
            log.info("params : {}", params); // param에 담긴 정보들을 한번에 로깅한다.

            return result;

        } catch (Throwable throwable) {
            throw throwable;
        }
    }
}
