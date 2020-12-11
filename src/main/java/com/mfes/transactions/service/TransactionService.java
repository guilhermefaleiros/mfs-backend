package com.mfes.transactions.service;

import com.mfes.transactions.dto.BalancesDTO;
import com.mfes.transactions.dto.CreateTransactionDTO;
import com.mfes.transactions.dto.DashboardDTO;
import com.mfes.transactions.dto.TransactionCsvDTO;
import com.mfes.transactions.models.Transaction;
import com.mfes.transactions.models.User;
import com.mfes.transactions.repository.TransactionRepository;
import com.mfes.transactions.repository.UserRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction create(CreateTransactionDTO createTransactionDTO) {
        User user = this.userRepository.findById(createTransactionDTO.getUserId()).get();
        if(user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        return this.transactionRepository.save(Transaction.builder()
                .description(createTransactionDTO.getDescription())
                .type(createTransactionDTO.getType())
                .title(createTransactionDTO.getTitle())
                .value(createTransactionDTO.getValue())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build());
    }

    public List<Transaction> upload(MultipartFile file, Long userId) {
        System.out.println(userId);
        User user = this.userRepository.findById(userId).get();

        if(user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        List<Transaction> transactions = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            CsvToBean<TransactionCsvDTO> csvDTOCsvToBean = new CsvToBeanBuilder(reader)
                    .withType(TransactionCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<TransactionCsvDTO> transactionCsvDTOS = csvDTOCsvToBean.parse();

            for(TransactionCsvDTO transactionCsvDTO : transactionCsvDTOS) {
                Transaction transaction = transactionCsvDTO.toNewTransaction(user);
                transactions.add(transaction);
                this.transactionRepository.save(transaction);
            }
        } catch(Exception e) {

        }
        return transactions;
    }

    public DashboardDTO findAll(Long userId) {
        User user = this.userRepository.findById(userId).get();
        if(user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        List<Transaction> transactions = this.transactionRepository.findByUser(user);
        BalancesDTO balancesDTO = getBalance(transactions);

        return DashboardDTO.builder()
                .transactions(transactions)
                .balance(balancesDTO)
                .build();

    }

    private BalancesDTO getBalance(List<Transaction> transactions) {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        AtomicReference<Double> outcome = new AtomicReference<>(0.0);
        AtomicReference<Double> income = new AtomicReference<>(0.0);

        transactions.forEach(transaction -> {
            if(transaction.getType().equals("incoming")) {
                income.updateAndGet(v -> v + transaction.getValue());
                total.updateAndGet(v -> v + transaction.getValue());
            } else {
                outcome.updateAndGet(v -> v + transaction.getValue());
                total.updateAndGet(v -> v - transaction.getValue());
            }
        });

        BalancesDTO balancesDTO = BalancesDTO.builder()
                .income(income.get())
                .total(total.get())
                .outcome(outcome.get())
                .build();

        return balancesDTO;
    }

}
