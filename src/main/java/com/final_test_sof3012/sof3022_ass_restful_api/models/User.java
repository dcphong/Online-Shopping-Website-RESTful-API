package com.final_test_sof3012.sof3022_ass_restful_api.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "Users")
public class User implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    String username;
    @Column(nullable = false)
    String password;
    @Column(unique = true, nullable = false)
    String email;
    @Column(nullable = false)
    String fullName;
    @Column(nullable = false)
    String address;
    @Column(nullable = false)
    String phone;
    @Column(nullable = true)
    String photo;
    @Column(nullable = false)
    Boolean available = true;

    @ElementCollection(targetClass = Roles.class, fetch =  FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "Roles", joinColumns = @JoinColumn(name = "user_id"))
    Set<Roles> roles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Order> orderList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    List<Product> productsList;
}
