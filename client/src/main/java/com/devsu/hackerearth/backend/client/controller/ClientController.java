package com.devsu.hackerearth.backend.client.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping("/")
	public ResponseEntity<List<ClientDto>> getAll() {
		// api/clients
		// Get all clients
		List<ClientDto> response = this.clientService.getAll();

		return ResponseEntity.status(HttpStatus.OK)
				.body(response);

	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id) {
		// api/clients/{id}
		// Get clients by id
		ClientDto response = this.clientService.getById(id);

		return ResponseEntity.status(HttpStatus.OK)
				.body(response);

	}

	@PostMapping("/")
	public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto) {
		// api/clients
		// Create client

		ClientDto response = this.clientService.create(clientDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(response);
	}

	@PostMapping("/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto) {
		// api/clients/{id}
		// Update client
		clientDto.setId(id);
		ClientDto response = this.clientService.update(clientDto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}

	@PutMapping("/api/accounts/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialClientDto partialClientDto) {
		// api/accounts/{id}
		// Partial update accounts
		ClientDto response = this.clientService.partialUpdate(id, partialClientDto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}

	@DeleteMapping("/api/clients/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		// api/clients/{id}
		// Delete client
		this.clientService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(null);
	}
}
