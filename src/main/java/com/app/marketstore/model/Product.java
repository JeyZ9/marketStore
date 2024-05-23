package com.app.marketstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private String mainImageUrl;

//@ElementCollection เพื่อบอกว่า additionalImages เป็น collection ของข้อมูล (ในที่นี้คือ List)
    @ElementCollection
//@CollectionTable เพื่อระบุชื่อของตารางที่ใช้เก็บข้อมูลเสริม (additional images)
    @CollectionTable(name = "additional_images", joinColumns = @JoinColumn(name = "product_id"))
//@Column เพื่อระบุชื่อคอลัมน์ที่ใช้เก็บ URL ของรูปภาพเสริมในตารางนั้น โดย @JoinColumn จะเชื่อมคอลัมน์ในตาราง additional_images กับคอลัมน์ product_id ในตารางหลักของสินค้า ทำให้สามารถเก็บข้อมูลเสริมที่เกี่ยวข้องกับสินค้าแต่ละรายการได้โดยตรง
    @Column(name = "image_url")
    private List<String> additionalImages;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;
}
