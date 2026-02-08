package com.terraria_item_perf_comp.config;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthTokenInterceptor authTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authTokenInterceptor)
                .addPathPatterns("/auth/refresh");
    }

    /**
     * Vue.js SPA 라우팅을 위한 설정
     * 루트 경로를 index.html로 리다이렉트
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    /**
     * 정적 리소스 핸들러 설정
     * 
     * SPA 라우팅 지원:
     * - 정적 파일(assets, favicon 등)이 존재하면 그대로 제공
     * - 파일이 없으면 index.html을 반환하여 Vue Router가 클라이언트 사이드에서 라우팅 처리
     * 
     * 이렇게 하면 컨트롤러 없이도 모든 경로를 처리할 수 있습니다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        
                        // API 경로는 제외 (컨트롤러가 처리)
                        if (resourcePath.startsWith("api/")) {
                            return null;
                        }
                        
                        // 정적 파일이 존재하고 읽을 수 있으면 반환
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        
                        // 파일이 없으면 index.html 반환 (SPA 라우팅)
                        // Vue Router가 클라이언트 사이드에서 경로를 처리
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }
}

