package com.Bank.DigitalBankSystem.config.mapper;

import com.Bank.DigitalBankSystem.dto.AccountDTO;
import com.Bank.DigitalBankSystem.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper( AccountMapper.class );

    AccountDTO toDto(Account entity);
    Account toEntity(AccountDTO dto);
}
