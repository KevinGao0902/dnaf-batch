package com.dnaf.batch.core.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

@Configuration
@Log4j2
public class XxlJobConfig {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appname}")
    private String appname;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        xxlJobSpringExecutor.setAccessToken(accessToken);

        // 自动获取本机IP
        try {
            String ip = getRealIp();
            xxlJobSpringExecutor.setIp(ip);
            log.info("获取当前服务器真实的IP: " + ip);
        } catch (Exception e) {
            log.error("获取当前服务器IP失败", e);
        }
        return xxlJobSpringExecutor;
    }

    /**
     * 获取物理网卡的真实IP
     */
    public String getRealIp() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            // 过滤掉回环接口、虚拟接口、未启用的接口
            if (networkInterface.isLoopback() ||
                    !networkInterface.isUp() ||
                    networkInterface.isVirtual() ||
                    networkInterface.getName().startsWith("docker") ||
                    networkInterface.getName().startsWith("veth") ||
                    networkInterface.getName().startsWith("br-")) {
                continue;
            }

            // 获取IPv4地址
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                    String ip = address.getHostAddress();
                    // 可以根据需要筛选特定网段的IP
                    if (ip.startsWith("172.24.")) {
                        return ip;
                    }
                }
            }
        }

        // 如果没有找到符合条件的IP，回退到原方法
        return InetAddress.getLocalHost().getHostAddress();
    }
}
