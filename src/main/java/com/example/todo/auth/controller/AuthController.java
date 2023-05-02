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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;


    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody RequestAuth.RegisterUserDto registerUserDto) {
        authService.registerUser(registerUserDto);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("User registered successfully.")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> loginUser(@Valid @RequestBody RequestAuth.LoginUserRqDto loginUserRqDto) {
        ResponseAuth.LoginUserRsDto response = authService.loginUser(loginUserRqDto).orElseThrow(()->new LoginFailedException());
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("User logged in successfully.")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateUser(HttpServletRequest request, @Valid @RequestBody RequestAuth.UpdateUserDto updateUserDto) {
        Optional<String> token = jwtAuthTokenProvider.getAuthToken(request);
        authService.updateUser(token, updateUserDto);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("User information updated successfully.")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseMessage> getUser(HttpServletRequest request){
        Optional<String> token = jwtAuthTokenProvider.getAuthToken(request);
        ResponseAuth.GetUserDto response = authService.getUser(token);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("User information retrieved successfully.")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
