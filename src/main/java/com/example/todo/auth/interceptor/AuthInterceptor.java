package com.example.todo.auth.interceptor;

import com.example.todo.auth.security.JwtAuthToken;
import com.example.todo.auth.security.JwtAuthTokenProvider;
import com.example.todo.common.exception.error.CustomJwtRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Optional<String> token = jwtAuthTokenProvider.getAuthToken(request);
        if(token.isPresent()){
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            if(jwtAuthToken.validate())
                return true;
            throw new CustomJwtRuntimeException();
        }
        throw new CustomJwtRuntimeException();
    }
}
