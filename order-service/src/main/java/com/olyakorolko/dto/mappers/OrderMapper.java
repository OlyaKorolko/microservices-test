package com.olyakorolko.dto.mappers;

import com.olyakorolko.domain.Order;
import com.olyakorolko.dto.OrderInDTO;
import com.olyakorolko.dto.OrderOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = OrderItemMapper.class)
public interface OrderMapper {
    Order fromDto(OrderInDTO orderInDTO);

    OrderOutDTO toDto(Order order);
}
