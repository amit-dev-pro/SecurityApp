package com.amit.SecurityApp.services;


import com.amit.SecurityApp.dto.LoginDto;
import com.amit.SecurityApp.dto.LoginResponseDto;
import com.amit.SecurityApp.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    public LoginResponseDto login(LoginDto loginDto){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())

        );

        User user=(User) authentication.getPrincipal();
        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);
        sessionService.generatedNewSession(user,refreshToken);

        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken){
        Long userId=jwtService.getUserIdFromToken(refreshToken);
        sessionService.validdateSession(refreshToken);
        User user=userService.getUserById(userId);

        String accessToken=jwtService.generateAccessToken(user);
        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }
}
