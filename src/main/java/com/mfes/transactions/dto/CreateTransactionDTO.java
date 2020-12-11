package com.mfes.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO {
    private Double value;
    private String type;
    private String title;
    private String description;
    private Long userId;
}
