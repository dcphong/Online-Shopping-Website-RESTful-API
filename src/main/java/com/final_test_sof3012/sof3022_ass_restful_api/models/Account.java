package com.final_test_sof3012.sof3022_ass_restful_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Email")
    private String email;

    @Column(name = "Photo")
    private String photo;

    @Column(name = "Activated")
    private Boolean activated;

    @Column(name = "Admin")
    private Boolean admin;

    @OneToMany(mappedBy = "account")
    List<Order> orderList;

}
