package com.cnblogs.hellxz.aop.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author Hellxz
 */
@Component
@Aspect
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * 应用名，需要在各服务配置 spring.application.name
     */
    private String appName;
    /**
     * 端口号，需要在各服务配置 server.port
     */
    private String serverPort;
    /**
     * 实例id，需要在各服务配置 instance-id：${random.value}
     */
    private String instanceId;

    public LoggingAspect(@Value("${spring.application.name}") String appName,
                         @Value("${server.port}") String serverPort,
                         @Value("${instance-id}") String instanceId){
        this.appName = appName;
        this.serverPort = serverPort;
        this.instanceId = instanceId;
    }



    /**
     * 定义切点，这里切在controller包下所有方法上
     */
    @Pointcut("within(com.cnblogs.hellxz.controller..*)")
    public void loggingPointCut(){}

    /**
     * 定义一个环绕通知，打印方法入参 与 出参
     * @param joinPoint 连接点
     * @return Object
     */
    @Around("loggingPointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        Object obj;

        try{
            // 入参日志
            logger.info("app_name:{}， server_port: {}， instance_id: {}，method_name：{} 被调用，in_args：{}", appName, serverPort, instanceId, joinPoint.getSignature().getName(), joinPoint.getArgs());
            obj = joinPoint.proceed();
            // 出参日志
            logger.info("app_name:{}， server_port: {}， instance_id: {}，method_name：{} 调用成功，out_args：{}", appName, serverPort, instanceId, joinPoint.getSignature().getName(), obj);
        }catch (Throwable e){
            throw new RuntimeException(e);
        }
        return obj;
    }
}
