package com.dnaf.batch.core.handler.demo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务参数类
 */
@Data
public  class JobParameters {
    private Map<String, String> params = new HashMap<>();

    public String getBusinessType() {
        return params.get("businessType");
    }

    public void setParam(String key, String value) {
        params.put(key, value);
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public String getParam(String key, String defaultValue) {
        return params.getOrDefault(key, defaultValue);
    }

    public boolean hasParam(String key) {
        return params.containsKey(key);
    }

    public Map<String, String> getCustomParams() {
        Map<String, String> customParams = new HashMap<>(params);
        customParams.remove("businessType");
        return customParams;
    }
}
