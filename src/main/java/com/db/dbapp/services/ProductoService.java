package com.db.dbapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dbapp.model.Producto;
import com.db.dbapp.repositories.ProductoRepository;

@Service
public class ProductoService implements IService<Producto> {

    private ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
	this.productoRepository = productoRepository;
    }

    public Producto obtenerPorId(Long id) throws Throwable {
	return productoRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("No existe el producto con el id " + id));
    }

    @Override
    public Producto crear(Producto entity) {
	return productoRepository.save(entity);
    }

    @Override
    public void actualizar(Producto entity) {
	productoRepository.save(entity);

    }

    @Override
    public void eliminar(Long id) {
	Producto producto;
	try {
	    producto = this.obtenerPorId(id);
	} catch (Throwable e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getLocalizedMessage());
	}
	productoRepository.delete(producto);

    }

    @Override
    public List<Producto> listar() {
	return (List<Producto>) productoRepository.findAll();
    }
    
    public List<Producto> buscar(String descripcion) {
    	return (List<Producto>) productoRepository.findByDescripcion(descripcion);
        }
    
}
