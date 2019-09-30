package com.example.mongdb.mongdb.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.stream.Stream;

/**
 * 实现基本的跨域请求
 * @author han
 *
 */
@Configuration
public class CorsConfig {

    private String corsAddress="http://localhost:8080";

    private final static String COMMA = ",";

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        /** 允许的域名使用可多个逗号隔开*/
        Stream.of(corsAddress.split(CorsConfig.COMMA))
                .forEach(address->corsConfiguration.addAllowedOrigin(address));
        // 允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}