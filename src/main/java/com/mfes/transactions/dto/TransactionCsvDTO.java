package com.mfes.transactions.dto;

import com.mfes.transactions.models.Transaction;
import com.mfes.transactions.models.User;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCsvDTO {

    @CsvBindByName
    private Double value;

    @CsvBindByName
    private String type;

    @CsvBindByName
    private String title;

    @CsvBindByName
    private String description;

    public Transaction toNewTransaction(User user) {
        return Transaction.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .value(this.value)
                .type(this.type)
                .title(this.title)
                .description(this.description)
                .build();
    }
}
