package com.devsu.hackerearth.backend.client.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {

	private Long id;
	private String dni;
	private String name;
	private String password;
	private String gender;
	private int age;
	private String address;
	private String phone;
	private boolean isActive;
}
