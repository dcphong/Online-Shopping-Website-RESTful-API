package com.final_test_sof3012.sof3022_ass_restful_api.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor@NoArgsConstructor@Builder
@Table(name = "Products")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private String image;
    private Boolean available;

    @Temporal(TemporalType.DATE)
    @Column(name = "createdate")
    private Date createdDate;

    @ManyToOne
    @JoinColumn( name = "categoryid")
    private Category category;

    @OneToMany( mappedBy = "product")
    List<OrderDetails> oderDetailsList;

}
