package com.example.todo.common.config;

import com.example.todo.auth.security.JwtAuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/secret/jwt-secret-key.properties")
@Configuration
public class JwtConfig {
    @Value("${jwt.secret}") //secret을 환경 변수에서 가져옴
    private String secret; // MysqlxDatatypes.Scalar -> ?

    @Bean
    public JwtAuthTokenProvider jwtProvider(){ //provider를 bean으로 등록
        return new JwtAuthTokenProvider(secret);
    }

}
