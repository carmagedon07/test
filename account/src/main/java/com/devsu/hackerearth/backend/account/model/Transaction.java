package com.devsu.hackerearth.backend.account.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends Base {

	private Date date;
	private String type;
	private double amount;
	private double balance;

	@Column(name = "account_id")
	private Long accountId;
}
