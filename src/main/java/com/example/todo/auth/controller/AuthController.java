package com.example.todo.auth.controller;

import com.example.todo.auth.dto.RequestAuth;
import com.example.todo.auth.dto.ResponseAuth;
import com.example.todo.auth.security.JwtAuthToken;
import com.example.todo.auth.security.JwtAuthTokenProvider;
import com.example.todo.auth.service.AuthService;
import com.example.todo.common.dto.ResponseMessage;
import com.example.todo.common.exception.error.LoginFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtAuthTokenProvider jwtAuthTokenProvider;


    @PostMapping("/api/auth/register")
    public ResponseEntity<ResponseMessage> userRegister(@Valid @RequestBody RequestAuth.register registerDto) {
        authService.register(registerDto);
        ResponseMessage response = ResponseMessage.builder()
                .message("Register Success")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<ResponseMessage> userLogin(@Valid @RequestBody RequestAuth.login loginDto) {
        ResponseAuth.login auth = authService.login(loginDto).orElseThrow(()->new LoginFailedException());
        ResponseMessage response = ResponseMessage.builder()
                .message("Login Success")
                .data(auth)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/auth/update")
    public ResponseEntity<ResponseMessage> userUpdate(HttpServletRequest request, @Valid @RequestBody RequestAuth.update updateDto) {
        Optional<String> token = jwtAuthTokenProvider.getAuthToken(request);
        String email = null;
        if (token.isPresent()) {
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            email = jwtAuthToken.getClaims().getSubject();
        }
        authService.update(email, updateDto);
        ResponseMessage response = ResponseMessage.builder()
                .message("Update Success")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/auth/read")
    public ResponseEntity<ResponseMessage> getUserInfo(HttpServletRequest request){
        Optional<String> token = jwtAuthTokenProvider.getAuthToken(request);
        String email = null;
        if(token.isPresent()){
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            email = jwtAuthToken.getClaims().getSubject();
        }
        ResponseAuth.info response =  authService.getUserInfo(email);
        return new ResponseEntity<>(ResponseMessage.builder()
                .message("Read Success")
                .data(response)
                .build(), HttpStatus.OK);
    }

}
