package com.db.dbapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dbapp.model.Cliente;
import com.db.dbapp.repositories.CarroRepository;
import com.db.dbapp.repositories.ClienteRepository;

@Service
public class ClienteService extends PersonaService<Cliente, Long> {

    ClienteRepository clienteRepository;
    CarroRepository carroRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, CarroRepository carroRepository) {
	this.clienteRepository = clienteRepository;
	this.carroRepository = carroRepository;
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
}
