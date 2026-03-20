package com.ecaj.dbankingbackend.operation.dtos;

import com.ecaj.dbankingbackend.operation.enums.OperationType;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}

