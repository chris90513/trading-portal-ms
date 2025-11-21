package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.AccountStatus;
import com.fxdidier.trading_portal.app.web.model.AccountStatusDto;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountStatusMapper {

    AccountStatusDto toDto(AccountStatus status);

    List<AccountStatusDto> toDtoList(List<AccountStatus> statuses);
}