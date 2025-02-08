package com.final_test_sof3012.sof3022_ass_restful_api.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Products")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "product_name",nullable = false)
    String name;
    @Column(nullable = false)
    Double price;
    @Column(nullable = false)
    String image;
    @Column(nullable = false)
    Boolean available;

    @Column(name = "discount_price",nullable = false)
    BigDecimal discountPrice;
    @Column(nullable = false)
    Long stock_quantity;
    @Column(nullable = false)
    Long sold_quantity;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonBackReference
    User user;

    @ManyToOne
    @JoinColumn( name = "category_id")
    Category category;

    @Column(nullable = true)
    String descriptions;

    @Column(name = "created_date",nullable = false)
    LocalDateTime createdDate;

    @OneToMany( mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<OrderDetails> orderDetailsList;

}
