package com.team.saver.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.saver.common.dto.ErrorMessage;
import com.team.saver.common.dto.ResponseMessage;
import com.team.saver.security.jwt.dto.Token;
import com.team.saver.security.jwt.support.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.team.saver.common.dto.ErrorMessage.*;
import static com.team.saver.common.dto.ResponseCode.REQUEST_FAIL;
import static com.team.saver.common.dto.ResponseCode.REQUEST_SUCCESS;

@Component
@RequiredArgsConstructor
public class FilterExceptionHandler extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (ServletException e) { // 토큰 만료
            if(!e.getCause().toString().contains("ExpiredJwtException")) {
                e.printStackTrace();

                setErrorResponse((HttpServletResponse) response, 400, UNKNOWN_EXCEPTION, e.getMessage());
                return;
            }
            Token token = jwtTokenProvider.reissueToken((HttpServletResponse) response, (HttpServletRequest) request);

            setSuccessResponse((HttpServletResponse) response, 200, TOKEN_REISSUE, token);
        } catch (IllegalStateException e) { // 바디 데이터 누락

            setErrorResponse((HttpServletResponse) response, 400, BODY_DATA_MISSING, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

            setErrorResponse((HttpServletResponse) response, 400, UNKNOWN_EXCEPTION, e.getMessage());
        }

    }

    private void setErrorResponse(HttpServletResponse response, int status, ErrorMessage errorMessage, String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(status);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseMessage errorResponse = ResponseMessage.of(REQUEST_FAIL ,errorMessage.getErrorCode(), errorMessage.getMessage(), message);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private void setSuccessResponse(HttpServletResponse response, int status, ErrorMessage errorMessage, Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(status);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseMessage successResponse = ResponseMessage.of(REQUEST_SUCCESS ,errorMessage.getErrorCode(), errorMessage.getMessage(), object);

        response.getWriter().write(objectMapper.writeValueAsString(successResponse));
    }

}
