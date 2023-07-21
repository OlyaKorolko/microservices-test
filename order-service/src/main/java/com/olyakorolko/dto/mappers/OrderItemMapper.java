package com.olyakorolko.dto.mappers;

import com.olyakorolko.domain.OrderItem;
import com.olyakorolko.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderItemMapper {
    OrderItem fromDto(OrderItemDTO orderItemDTO);

    OrderItemDTO toDto(OrderItem orderItem);
}
