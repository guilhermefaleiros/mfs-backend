package com.mfes.transactions.controller;

import com.mfes.transactions.dto.CreateTransactionDTO;
import com.mfes.transactions.dto.DashboardDTO;
import com.mfes.transactions.models.Transaction;
import com.mfes.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;


@Controller
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody CreateTransactionDTO dto) {
        return new ResponseEntity<Transaction>(transactionService.create(dto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/upload/{userId}", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @PathVariable Long userId) {
        return new ResponseEntity<List<Transaction>>(transactionService.upload(file, userId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Long userId) {
        return new ResponseEntity<DashboardDTO>(transactionService.findAll(userId), HttpStatus.CREATED);
    }
}
