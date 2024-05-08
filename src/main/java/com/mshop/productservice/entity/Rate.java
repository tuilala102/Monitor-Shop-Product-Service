package com.mshop.productservice.entity;

import com.mshop.productservice.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double star;
    private String comment;
    private Date rateDate;

    private Long userId;

    @Transient
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
