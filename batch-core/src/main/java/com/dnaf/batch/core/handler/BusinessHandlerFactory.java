package com.dnaf.batch.core.handler;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BusinessHandlerFactory {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<String, BusinessHandler> handlerMap = new HashMap<>();

    @PostConstruct
    public void init() {
        // 自动发现所有BusinessHandler的实现
        Map<String, BusinessHandler> handlers = applicationContext
                .getBeansOfType(BusinessHandler.class);

        for (BusinessHandler handler : handlers.values()) {
            String businessType = handler.getBusinessType();
            if (handlerMap.containsKey(businessType)) {
                log.warn("发现重复的业务处理器类型: {}", businessType);
            } else {
                handlerMap.put(businessType, handler);
                log.info("注册业务处理器: {}", businessType);
            }
        }

        log.info("业务处理器工厂初始化完成，共注册{}个处理器", handlerMap.size());
    }

    /**
     * 根据业务类型获取处理器
     */
    public BusinessHandler getHandler(String businessType) {
        BusinessHandler handler = handlerMap.get(businessType);
        if (handler == null) {
            log.error("未找到业务类型对应的处理器: {}", businessType);
            log.info("可用的处理器类型: {}", handlerMap.keySet());
        }
        return handler;
    }

    /**
     * 获取所有可用的业务类型
     */
    public List<String> getAvailableBusinessTypes() {
        return new ArrayList<>(handlerMap.keySet());
    }

    /**
     * 检查业务类型是否支持
     */
    public boolean isSupported(String businessType) {
        return handlerMap.containsKey(businessType);
    }
}
