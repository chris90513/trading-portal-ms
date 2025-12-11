package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.Account;
import com.fxdidier.trading_portal.app.web.model.AccountDetailDto;
import com.fxdidier.trading_portal.app.web.model.AccountSummaryDto;
import com.fxdidier.trading_portal.app.web.model.AccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    // ========== Entity -> Summary DTO ==========
    @Mapping(target = "brokerName",      source = "broker.name")
    @Mapping(target = "accountTypeName", source = "accountType.name")
    @Mapping(target = "statusName",      source = "status.name")
    @Mapping(target = "evaluationProgramName", source = "evaluationProgram.name")
    @Mapping(target = "evaluationStepName",    source = "evaluationStep.name")
    AccountSummaryDto toSummaryDto(Account account);

    // ========== Entity -> Detail DTO ==========
    @Mapping(target = "userId",                  source = "user.id")
    @Mapping(target = "userUsername",            source = "user.username") // ajusta si es email u otro campo
    @Mapping(target = "brokerId",                source = "broker.id")
    @Mapping(target = "brokerName",              source = "broker.name")
    @Mapping(target = "accountTypeId",           source = "accountType.id")
    @Mapping(target = "accountTypeName",         source = "accountType.name")
    @Mapping(target = "evaluationProgramId",     source = "evaluationProgram.id")
    @Mapping(target = "evaluationProgramName",   source = "evaluationProgram.name")
    @Mapping(target = "evaluationStepId",        source = "evaluationStep.id")
    @Mapping(target = "evaluationStepName",      source = "evaluationStep.name")
    @Mapping(target = "statusId",                source = "status.id")
    @Mapping(target = "statusName",              source = "status.name")
    @Mapping(target = "previousAccountId",       source = "previousAccount.id")
    @Mapping(target = "previousAccountNumber",   source = "previousAccount.accountNumber")
    AccountDetailDto toDetailDto(Account account);

    // ========== Request -> Entity (CREATE) ==========
    @Mapping(target = "id",                ignore = true)
    @Mapping(target = "user",              ignore = true)
    @Mapping(target = "broker",            ignore = true)
    @Mapping(target = "accountType",       ignore = true)
    @Mapping(target = "evaluationProgram", ignore = true)
    @Mapping(target = "evaluationStep",    ignore = true)
    @Mapping(target = "status",            ignore = true)
    @Mapping(target = "previousAccount",   ignore = true)
    Account toEntity(AccountRequest request);

    // ========== Request -> Entity (UPDATE) ==========
    @Mapping(target = "id",                ignore = true)
    @Mapping(target = "user",              ignore = true)
    @Mapping(target = "broker",            ignore = true)
    @Mapping(target = "accountType",       ignore = true)
    @Mapping(target = "evaluationProgram", ignore = true)
    @Mapping(target = "evaluationStep",    ignore = true)
    @Mapping(target = "status",            ignore = true)
    @Mapping(target = "previousAccount",   ignore = true)
    void updateEntityFromRequest(AccountRequest request, @MappingTarget Account account);
}
