package com.db.dbapp.services;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dbapp.model.Cliente;
import com.db.dbapp.repositories.ClienteRepository;
import com.db.dbapp.repositories.CompraRepository;

@Service
public class ClienteService extends PersonaService<Cliente, Long> {

    ClienteRepository clienteRepository;
    CompraRepository compraRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, CompraRepository compraRepository) {
	this.clienteRepository = clienteRepository;
	this.compraRepository = compraRepository;
    }

    public Cliente nuevo(Cliente cliente) {
	try {
	    return clienteRepository.save(cliente);

	} catch (ConstraintViolationException e) {
	    throw new RuntimeException("No puede crearse el cliente por una violacion a una restriccion");
	}
    }

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

}
