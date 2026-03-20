package com.ecaj.dbankingbackend.operation.mappers;

import com.ecaj.dbankingbackend.operation.dtos.AccountOperationDTO;
import com.ecaj.dbankingbackend.operation.entities.AccountOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class OperationMapperImpl {
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }
}
