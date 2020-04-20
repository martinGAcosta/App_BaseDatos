package com.db.dbapp.mappers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.db.dbapp.model.Carro;
import com.db.dbapp.model.Cliente;
import com.db.dbapp.model.dtos.CarroDto;
import com.db.dbapp.model.dtos.ClienteDto;
import com.db.dbapp.model.dtos.VendedorDto;

public class ClienteMapper {

    private ClienteMapper instance;

    private ClienteMapper() {
	instance = new ClienteMapper();
    }

    public static Cliente toCliente(Cliente cliente, Set<Carro> carros, ClienteDto clienteDto) {
	cliente.setNombre(clienteDto.getNombre());
	cliente.setApellido(clienteDto.getApellido());
	cliente.setDni(clienteDto.getDni());
	cliente.setEmail(clienteDto.getEmail());
	cliente.setCompras(carros);
	return cliente;
    }

    public static Cliente toCliente(Cliente cliente, ClienteDto clienteDto) {
	cliente.setNombre(clienteDto.getNombre());
	cliente.setApellido(clienteDto.getApellido());
	cliente.setDni(clienteDto.getDni());
	cliente.setEmail(clienteDto.getEmail());
	cliente.setCompras(new HashSet<Carro>());
	return cliente;
    }

    public static ClienteDto toClienteDto(ClienteDto clienteDto, Cliente cliente, Set<Carro> carros) {
	clienteDto.setApellido(cliente.getApellido());
	clienteDto.setNombre(cliente.getNombre());
	clienteDto.setDni(cliente.getDni());
	clienteDto.setEmail(cliente.getEmail());
	clienteDto.setId(cliente.getId());
	if (carros != null)
	    clienteDto.setCompras(
		    carros.stream().map(c -> CarroMapper.toCarroDto(new CarroDto(), c)).collect(Collectors.toSet()));
	clienteDto.setVendedor(
		cliente.getVendedor() != null ? VendedorMapper.toVendedorDto(new VendedorDto(), cliente.getVendedor())
			: new VendedorDto());
	return clienteDto;
    }

    public ClienteMapper getInstance() {
	return instance;
    }

    public static Set<ClienteDto> toClienteDtoSet(Collection<Cliente> clientes) {
	return clientes == null ? new HashSet<>()
		: clientes.stream().map(cliente -> ClienteMapper.toClienteDto(new ClienteDto(), cliente, null))
			.collect(Collectors.toSet());
    }
}
