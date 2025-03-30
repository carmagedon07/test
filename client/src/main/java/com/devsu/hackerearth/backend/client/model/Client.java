package com.devsu.hackerearth.backend.client.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
//@Builder
public class Client extends Person {
	private String password;
	private boolean isActive;
}
