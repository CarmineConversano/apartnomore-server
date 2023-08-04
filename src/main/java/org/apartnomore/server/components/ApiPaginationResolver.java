package org.apartnomore.server.components;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
public class ApiPaginationResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        int end = Integer.parseInt(Objects.requireNonNull(nativeWebRequest.getParameter("_end")));
        int start = Integer.parseInt(Objects.requireNonNull(nativeWebRequest.getParameter("_start")));
        int pageSize = end - start;
        int page = start / pageSize;

        return PageRequest.of(page, pageSize);
    }
}
