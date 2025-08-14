package com.tucfinancymanager.backend.DTOs.transaction;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionAmountDTO {

    private BigDecimal income;

    private BigDecimal expense;

    private BigDecimal total;

}
