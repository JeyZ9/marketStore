package com.app.marketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CartDTO {

    private List<CartItemDTO> cartItemDTOS;

    private Double totalCost;
}
