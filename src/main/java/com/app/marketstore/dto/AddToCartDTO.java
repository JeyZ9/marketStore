package com.app.marketstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddToCartDTO {

    private Long id;

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;
}
