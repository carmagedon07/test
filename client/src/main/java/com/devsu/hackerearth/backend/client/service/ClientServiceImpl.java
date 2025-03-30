package com.devsu.hackerearth.backend.client.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		// Get all clients
		List<ClientDto> response = null;
		List<Object[]> responseQuery = this.clientRepository.findAllClients();
		if (!responseQuery.isEmpty()) {
			response = new ArrayList();
			for (Object[] objctResponse : responseQuery) {
				response.add(this.mapToClientDto(objctResponse));
			}
		}
		return response;
	}

	@Override
	public ClientDto getById(Long id) {
		// Get clients by id
		ClientDto response = null;
		List<Object[]> responseQuery = this.clientRepository.findAllClients();
		if (responseQuery.size() == 1) {
			response = this.mapToClientDto(responseQuery.get(0));
		}

		return response;
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		// Create client
		Client clientDb = this.mapDtoToEntity(clientDto);
		this.clientRepository.insertClient(clientDb);
		return clientDto;
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		Client clientDb = this.mapDtoToEntity(clientDto);
		this.clientRepository.updateClient(clientDb);
		return clientDto;
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		// Partial update
		this.clientRepository.updateClientStatus(id, partialClientDto.isActive());
		return this.getById(id);
	}

	@Override
	public void deleteById(Long id) {
		// Delete client
		this.clientRepository.deleteById(id);
	}

	private ClientDto mapToClientDto(Object[] objctResponse) {

		return new ClientDto().builder()
				.id(objctResponse[0] != null ? Long.valueOf(objctResponse[0].toString()) : null)
				.dni(objctResponse[1] != null ? objctResponse[1].toString() : null)
				.name(objctResponse[2] != null ? objctResponse[2].toString() : null)
				.password(objctResponse[3] != null ? objctResponse[3].toString() : null)
				.gender(objctResponse[4] != null ? objctResponse[4].toString() : null)
				.age(objctResponse[5] != null ? Integer.valueOf(objctResponse[5].toString()) : 0)
				.address(objctResponse[6] != null ? objctResponse[6].toString() : null)
				.phone(objctResponse[7] != null ? objctResponse[7].toString() : null)
				.isActive(objctResponse[8] != null ? Boolean.valueOf(objctResponse[8].toString()) : false)
				.build();

	}

	private Client mapDtoToEntity(ClientDto clientDto) {
		Client client = new Client();
		client.setId(clientDto.getId());
		client.setDni(clientDto.getDni());
		client.setName(clientDto.getName());
		client.setPassword(clientDto.getPassword());
		client.setGender(clientDto.getGender());
		client.setAge(clientDto.getAge());
		client.setAddress(clientDto.getAddress());
		client.setPhone(clientDto.getPhone());
		client.setActive(clientDto.isActive());
		return client;
	}
}
