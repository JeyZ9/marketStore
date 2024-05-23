package com.app.marketstore.dto;

import com.app.marketstore.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private String description;

    @NotBlank
    private Double price;

    @NotBlank
    private String mainImageUrl;

    @NotBlank
    private List<String> additionalImages;

    @NotBlank
    private Long categoryId;

    public ProductDTO(String name, String description, Double price, String mainImageUrl, List<String> additionalImages, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.mainImageUrl = mainImageUrl;
        this.additionalImages = additionalImages;
        this.categoryId = categoryId;
    }

    public ProductDTO(Product product){
        this.setId(product.getId());
        this.setName(product.getName());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setMainImageUrl(product.getMainImageUrl());
        this.setAdditionalImages(product.getAdditionalImages());
        this.setCategoryId(product.getCategory().getId());
    }
}
