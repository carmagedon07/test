package com.devsu.hackerearth.backend.account.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/")
	public ResponseEntity<List<AccountDto>> getAll() {
		// api/accounts
		try {
			List<AccountDto> accounts = this.accountService.getAll();
			if (accounts.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(accounts);
		} catch (Exception e) {
			e.getStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}

	}

	@GetMapping("/id")
	public ResponseEntity<AccountDto> get(@PathVariable Long id) {
		// api/accounts/{id}
		// Get accounts by id
		try {
			AccountDto cuenta = this.accountService.getById(id);
			if (cuenta == null) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(cuenta);

		} catch (Exception e) {
			e.getStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}

	}

	public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
		// api/accounts
		// Create accounts
		try {
			AccountDto cuenta = this.accountService.create(accountDto);
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(cuenta.getId())
					.toUri();

			return ResponseEntity.created(location).body(cuenta);

		} catch (Exception e) {
			e.getStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}

	}

	@PostMapping("/id")
	public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto) {
		// api/accounts/{id}
		// Update accounts
		try {
			accountDto.setId(id);
			AccountDto response = this.accountService.update(accountDto);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(response);
		} catch (Exception e) {
			e.getStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}

	}

	@PutMapping("/id")
	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialAccountDto partialAccountDto) {
		// api/accounts/{id}
		// Partial update accounts
		try {
			AccountDto response = this.accountService.partialUpdate(id, partialAccountDto);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(response);
		} catch (Exception e) {
			e.getStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}

	}

	public ResponseEntity<Void> delete(@PathVariable Long id) {
		// api/accounts/{id}
		// Delete accounts
		try {

			return ResponseEntity.status(HttpStatus.OK)
					.body(null);
		} catch (Exception e) {
			e.getStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}

	}
}
