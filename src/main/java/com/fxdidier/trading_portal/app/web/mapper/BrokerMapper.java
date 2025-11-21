package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.Broker;
import com.fxdidier.trading_portal.app.web.model.BrokerDto;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BrokerMapper {

    BrokerDto toDto(Broker broker);
    List<BrokerDto> toDtoList(List<Broker> brokers);
}