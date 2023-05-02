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
    public void registerUser(RequestAuth.RegisterUserDto registerUserDto) {

        Auth user = authRepository.findByEmail(registerUserDto.getEmail());
        if(user != null){
            throw new RegisterFailedException();
        }
        user = authRepository.findByUsername(registerUserDto.getUsername());
        if(user != null){
            throw new RegisterFailedException();
        }

        String salt = SHA256Util.generateSalt();
        String encryptedPassword = SHA256Util.getEncrypt(registerUserDto.getPassword(),salt);
        user = RequestAuth.RegisterUserDto.toEntity(registerUserDto, salt, encryptedPassword);
        authRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<ResponseAuth.LoginUserRsDto> loginUser(RequestAuth.LoginUserRqDto loginUserRqDto) {
        Auth user = authRepository.findByEmail(loginUserRqDto.getEmail());
        if(user == null)
            throw new LoginFailedException();

        String salt = user.getSalt();
        user = authRepository.findByEmailAndPassword(loginUserRqDto.getEmail(), SHA256Util.getEncrypt(loginUserRqDto.getPassword(),salt));
        if(user == null)
            throw new LoginFailedException();

        String accessToken = createAccessToken(user.getEmail());
        return Optional.ofNullable(ResponseAuth.LoginUserRsDto.toDto(accessToken));
    }

    @Override
    public String createAccessToken(String userid) {
        Date expiredDate = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(userid, Role.USER.getCode(),expiredDate);
        return accessToken.getToken();
    }

    @Override
    @Transactional
    public void updateUser(Optional<String> token, RequestAuth.UpdateUserDto updateUserDto) {

        String email = null;
        if (token.isPresent()) {
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            email = jwtAuthToken.getClaims().getSubject();
        }

        Auth originalUser = authRepository.findByEmail(email);
        if(originalUser == null)
            throw new NotFoundUserException();
        Auth nameUser = authRepository.findByUsername(updateUserDto.getUsername());
        if(nameUser != null && !originalUser.equals(nameUser))
            throw new RegisterFailedException();

        String salt = SHA256Util.generateSalt();
        String encryptedPassword = SHA256Util.getEncrypt(updateUserDto.getPassword(), salt);
        Auth updatedUser = RequestAuth.UpdateUserDto.toEntity(originalUser, updateUserDto, salt, encryptedPassword);
        authRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public ResponseAuth.GetUserDto getUser(Optional<String> token) {

        String email = null;
        if(token.isPresent()){
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            email = jwtAuthToken.getClaims().getSubject();
        }
        Auth user = authRepository.findByEmail(email);
        if (user == null)
            throw new NotFoundUserException();

        return ResponseAuth.GetUserDto.toDto(user);
    }
}
