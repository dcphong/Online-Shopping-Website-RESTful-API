package com.final_test_sof3012.sof3022_ass_restful_api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name="Order_Details")
public class OrderDetails implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;

    Long quantity;
    Double price;
    Double total;
    String descriptions;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    @JsonBackReference
    Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    Order order;

}
