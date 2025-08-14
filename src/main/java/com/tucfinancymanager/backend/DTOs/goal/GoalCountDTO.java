package com.tucfinancymanager.backend.DTOs.goal;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalCountDTO {

    private Long inProgress;

    private Long completed;

    private Long expired;

}
