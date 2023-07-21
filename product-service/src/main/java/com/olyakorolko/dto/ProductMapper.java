package com.olyakorolko.dto;

import com.olyakorolko.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    Product fromDto(ProductInDTO productInDTO);

    ProductOutDTO toDto(Product product);
}
