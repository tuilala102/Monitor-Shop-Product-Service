package com.mshop.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private String name;
	private int quantity;
	private Double price;
	private Double discount;
//	@Column(columnDefinition = "LONGBLOB")
	private String image;
	private String description;
	private Date enteredDate;
	private Boolean status;
	
//	@OneToMany(mappedBy = "product")
//	private List<Rate> rates;	
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
}
