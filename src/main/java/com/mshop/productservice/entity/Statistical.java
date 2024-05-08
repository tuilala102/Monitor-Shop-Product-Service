package com.mshop.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistical implements Serializable{
	private int month;
	private Date date;
	private Double amount;
	private BigInteger count;
}
