package com.db.dbapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dbapp.model.Compra;
import com.db.dbapp.repositories.CompraRepository;

@Service
public class CompraService implements IService<Compra> {

    CompraRepository compraRepository;

    @Autowired
    public CompraService(CompraRepository compraRepository) {
	this.compraRepository = compraRepository;
    }

    @Override
    public Compra obtenerPorId(Long id) {
	return compraRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("No se puede encontrar la compra con ID " + id));
    }

    @Override
    public Compra crear(Compra entity) {
	return compraRepository.save(entity);
    }

    @Override
    public void actualizar(Compra entity) {
	compraRepository.save(entity);

    }

    @Override
    public void eliminar(Long id) {
	Compra compra = this.obtenerPorId(id);
	compraRepository.delete(compra);
    }

    @Override
    public List<Compra> listar() {
	return (List<Compra>) compraRepository.findAll();
    }
}
