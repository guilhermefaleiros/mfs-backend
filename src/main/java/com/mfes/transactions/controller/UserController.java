package com.mfes.transactions.controller;

import com.mfes.transactions.dto.CreateTransactionDTO;
import com.mfes.transactions.dto.CreateUserDTO;
import com.mfes.transactions.dto.UserDTO;
import com.mfes.transactions.models.Transaction;
import com.mfes.transactions.models.User;
import com.mfes.transactions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody CreateUserDTO dto) {
        return new ResponseEntity<User>(this.userService.create(dto), HttpStatus.CREATED);
    }


}
