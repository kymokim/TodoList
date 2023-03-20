package com.example.todo.auth.service;

import com.example.todo.auth.domain.Auth;
import com.example.todo.auth.dto.RequestAuth;
import com.example.todo.auth.dto.ResponseAuth;
import com.example.todo.auth.repository.AuthRepository;
import com.example.todo.auth.security.JwtAuthToken;
import com.example.todo.auth.security.JwtAuthTokenProvider;
import com.example.todo.auth.security.role.Role;
import com.example.todo.auth.util.SHA256Util;
import com.example.todo.common.exception.error.LoginFailedException;
import com.example.todo.common.exception.error.NotFoundUserException;
import com.example.todo.common.exception.error.RegisterFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface {

    private final AuthRepository authRepository;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    //private final S3Service s3Service; // aws

    @Transactional
    @Override
    public void register(RequestAuth.register registerDto) {
        Auth user = authRepository.findByEmail(registerDto.getEmail());
        if(user != null){
            throw new RegisterFailedException();
        }

        user = authRepository.findByUsername(registerDto.getUsername());
        if(user != null){
            throw new RegisterFailedException();
        }

        String salt = SHA256Util.generateSalt();

        String encryptedPassword = SHA256Util.getEncrypt(registerDto.getPassword(),salt);

        user = Auth.builder()
                .email(registerDto.getEmail())
                .password(encryptedPassword)
                .username(registerDto.getUsername())
                .salt(salt)
                .build();
        authRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<ResponseAuth.login> login(RequestAuth.login loginDto) {
        Auth user = authRepository.findByEmail(loginDto.getEmail());
        if(user == null)
            throw new LoginFailedException();

        String salt = user.getSalt();
        user = authRepository.findByEmailAndPassword(loginDto.getEmail(), SHA256Util.getEncrypt(loginDto.getPassword(),salt));
        if(user == null)
            throw new LoginFailedException();

        ResponseAuth.login login = ResponseAuth.login.builder()
                .accessToken(createAccessToken(user.getEmail()))
                .build();

        return Optional.ofNullable(login);
    }

    @Override
    public String createAccessToken(String userid) {
        Date expiredDate = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(userid, Role.USER.getCode(),expiredDate);
        return accessToken.getToken();
    }

    @Override
    @Transactional
    public void update(String email, RequestAuth.update updateDto) {
        Auth user = authRepository.findByEmail(email);
        if(user == null)
            throw new NotFoundUserException();
        Auth user1 = authRepository.findByUsername(updateDto.getUsername());
        if(user1 != null && !user.equals(user1))
            throw new RegisterFailedException();

        String salt = SHA256Util.generateSalt();
        String encryptedPassword = SHA256Util.getEncrypt(updateDto.getPassword(), salt);
        user.update(encryptedPassword,updateDto.getUsername(),salt);
    }

    @Override
    @Transactional
    public ResponseAuth.info getUserInfo(String email) {
        Auth user = authRepository.findByEmail(email);
        if (user == null)
            throw new NotFoundUserException();
        ResponseAuth.info response = ResponseAuth.info.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
        return response;
    }
}
