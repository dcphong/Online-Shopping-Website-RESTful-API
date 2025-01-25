package com.final_test_sof3012.sof3022_ass_restful_api.models;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    @Temporal(TemporalType.DATE)
    @Column(name = "createdate")
    private Date createdDate = new Date();
    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;
    @OneToMany(mappedBy = "order")
    List<OrderDetails> orderDetailsList;


}
