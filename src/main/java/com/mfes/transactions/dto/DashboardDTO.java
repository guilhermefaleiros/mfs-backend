package com.mfes.transactions.dto;

import com.mfes.transactions.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private BalancesDTO balance;
    private List<Transaction> transactions;
}
