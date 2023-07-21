package com.olyakorolko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderOutDTO {
    private Long id;
    private String orderNumber;
    private List<OrderItemDTO> items;
}
