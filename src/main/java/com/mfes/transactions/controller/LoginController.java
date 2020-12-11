package com.mfes.transactions.controller;

import com.mfes.transactions.dto.AccessLoginResponseDTO;
import com.mfes.transactions.dto.CreateTransactionDTO;
import com.mfes.transactions.dto.LoginRequestDTO;
import com.mfes.transactions.models.Transaction;
import com.mfes.transactions.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        return new ResponseEntity<AccessLoginResponseDTO>(this.loginService.login(dto), HttpStatus.CREATED);
    }
}
