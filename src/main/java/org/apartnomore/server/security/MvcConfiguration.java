package org.apartnomore.server.security;

import org.apartnomore.server.components.ApiPaginationResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private final ApiPaginationResolver apiPaginationResolver;

    public MvcConfiguration(ApiPaginationResolver apiPaginationResolver) {
        this.apiPaginationResolver = apiPaginationResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(apiPaginationResolver);
    }
}
