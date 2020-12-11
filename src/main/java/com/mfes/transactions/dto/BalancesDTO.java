package com.mfes.transactions.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalancesDTO {
    Double outcome;
    Double total;
    Double income;
}
