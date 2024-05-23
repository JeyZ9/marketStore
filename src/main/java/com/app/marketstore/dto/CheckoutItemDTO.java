package com.app.marketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckoutItemDTO {

    private String productName;

    private Long quantity;

    private double price;

    private Long productId;
}
