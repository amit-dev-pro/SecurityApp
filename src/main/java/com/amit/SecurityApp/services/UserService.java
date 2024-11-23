package com.amit.SecurityApp.services;

import com.amit.SecurityApp.dto.SignUpDto;
import com.amit.SecurityApp.dto.UserDto;
import com.amit.SecurityApp.entities.User;
import com.amit.SecurityApp.exception.ResourceNotFoundException;
import com.amit.SecurityApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmail(username)
                .orElseThrow(()->new BadCredentialsException("User with email "+username+" not found"));

    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id "+ userId +
                " not found"));
    }

    public User getUsrByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDto signUp(SignUpDto signUpDto){
        Optional<User> user=userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("user eith email already exits "+signUpDto.getEmail());

        }

        User toBeCreatedUser=modelMapper.map(signUpDto,User.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

        User savedUser=userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser,UserDto.class);
    }

    public User save(User newUser){
        return userRepository.save(newUser);
    }
}
