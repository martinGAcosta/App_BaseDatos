package com.db.dbapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dbapp.model.Vendedor;
import com.db.dbapp.repositories.VendedorRepository;

@Service
public class VendedorService extends PersonaService<Vendedor, Long> {

    private VendedorRepository vendedorRepository;

    @Autowired
    public VendedorService(VendedorRepository vendedorRepository) {
	this.vendedorRepository = vendedorRepository;
    }

    @Override
    public Vendedor obtenerPorId(Long id) throws Throwable {
	Object o = super.obtenerPorId(id);
	try {
	    return (Vendedor) o;
	} catch (ClassCastException e) {
	    throw new RuntimeException("El id " + id + " no corresponde a un vendedor valido");
	}
    }

    @Override
    public Vendedor crear(Vendedor entity) {
	return vendedorRepository.save(entity);
    }

    @Override
    public void actualizar(Vendedor entity) {
	vendedorRepository.save(entity);

    }

    @Override
    public void eliminar(Long id) {
	Vendedor vendedor;
	// String tipo = "VENDEDOR";
	try {
	    vendedor = this.obtenerPorId(id);
	} catch (Throwable e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getLocalizedMessage());
	}
	vendedorRepository.delete(vendedor);

    }

    @Override
    public List<Vendedor> listar() {
	return (List<Vendedor>) vendedorRepository.findAll();
    }
}
