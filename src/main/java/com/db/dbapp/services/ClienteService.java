package com.db.dbapp.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dbapp.mappers.ClienteMapper;
import com.db.dbapp.model.Carro;
import com.db.dbapp.model.Cliente;
import com.db.dbapp.model.Vendedor;
import com.db.dbapp.model.dtos.ClienteDto;
import com.db.dbapp.repositories.CarroRepository;
import com.db.dbapp.repositories.ClienteRepository;
import com.db.dbapp.services.VendedorService;

@Service
public class ClienteService extends PersonaService<Cliente, Long> {

    ClienteRepository clienteRepository;
    CarroRepository carroRepository;
    private VendedorService vendedorService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, CarroRepository carroRepository, VendedorService vendedorService) {
	this.clienteRepository = clienteRepository;
	this.carroRepository = carroRepository;
	this.vendedorService = vendedorService;
    }

    @Override
    public void actualizar(Cliente cliente) {
	clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(Long id) {
	Cliente cliente;
	try {
	    cliente = this.obtenerPorId(id);
	} catch (Throwable e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getLocalizedMessage());
	}
	clienteRepository.delete(cliente);
    }

    @Override
    public List<Cliente> listar() {
	return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    public Cliente crear(Cliente entity) {
	return (Cliente) clienteRepository.save(entity);
    }

    @Override
    public Cliente obtenerPorId(Long id) throws Throwable {
	Object o = super.obtenerPorId(id);
	try {
	    return (Cliente) o;
	} catch (ClassCastException e) {
	    throw new RuntimeException("El id " + id + " no corresponde a un cliente valido");
	}
    }
    
    public ClienteDto reasignarVendedor(Long idCliente, Long idVendedor) throws Throwable {
    	Cliente cliente = obtenerPorId(idCliente);
	    Vendedor vendedor = vendedorService.obtenerPorId(idVendedor);
	    if (cliente.getVendedor() != null) {
		cliente.getVendedor().removeCliente(cliente);
	    }
	    cliente.setVendedor(vendedor);
	    vendedor.addCliente(cliente);
	    actualizar(cliente);
	    vendedorService.actualizar(vendedor);

	   ClienteDto clienteDto = ClienteMapper.toClienteDto(new ClienteDto(), cliente, null);
	   return clienteDto;
    }
    
    public ClienteDto crearCliente(Long idVendedor, ClienteDto clienteDto) throws Throwable {
    	Vendedor vendedor = vendedorService.obtenerPorId(idVendedor);
	    Cliente cliente = ClienteMapper.toCliente(new Cliente(), new HashSet<Carro>(), clienteDto);
	    cliente.setVendedor(vendedor);
		clienteRepository.save(cliente);
	    vendedor.addCliente(cliente);
	    vendedorService.actualizar(vendedor);
	    return clienteDto = ClienteMapper.toClienteDto(clienteDto, cliente, null);}
}
