package com.mfes.transactions.service;

import com.mfes.transactions.dto.CreateUserDTO;
import com.mfes.transactions.models.User;
import com.mfes.transactions.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jodd.crypt.BCrypt;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(CreateUserDTO createUserDTO) {
        return this.userRepository.save(User.builder()
                .name(createUserDTO.getName())
                .email(createUserDTO.getEmail())
                .password(BCrypt.hashpw(createUserDTO.getPassword(), BCrypt.gensalt()))
                .build());
    }

}
