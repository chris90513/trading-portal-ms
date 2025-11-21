package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.AccountType;
import com.fxdidier.trading_portal.app.web.model.AccountTypeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {

    AccountTypeDto toDto(AccountType accountType);

    List<AccountTypeDto> toDtoList(List<AccountType> accountTypes);
}
