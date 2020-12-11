package com.mfes.transactions.service;

import com.mfes.transactions.dto.AccessLoginResponseDTO;
import com.mfes.transactions.dto.LoginRequestDTO;
import com.mfes.transactions.dto.UserDTO;
import com.mfes.transactions.models.User;
import com.mfes.transactions.repository.UserRepository;
import jodd.crypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public AccessLoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = this.userRepository.findByEmail(loginRequestDTO.getEmail());
        if(user != null) {
            if(BCrypt.checkpw(loginRequestDTO.getPassword(), user.getPassword())) {
                return AccessLoginResponseDTO.builder()
                        .access(true)
                        .user(UserDTO.builder()
                                .name(user.getName())
                                .email(user.getEmail())
                                .id(user.getId())
                                .build())
                        .build();
            }
        }
        return AccessLoginResponseDTO.builder()
                .access(false)
                .user(null)
                .build();
    }

}
