package com.team.saver.common.util;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.team.saver.security.jwt.support.JwtAuthenticationFilter.getTokenFromCookie;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(LogIn.class);
        boolean hasUserType = CurrentUser.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String token = getTokenFromCookie(request);
        String email = jwtTokenProvider.getUserPk(token);

        return new CurrentUser(email);
    }
}
