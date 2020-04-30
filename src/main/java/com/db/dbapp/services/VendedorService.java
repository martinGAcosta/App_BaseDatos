package com.db.dbapp.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dbapp.model.Cliente;
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

    public void transferirClientes(Long idDesde, Long idHasta) throws Throwable {
	Vendedor desde = this.obtenerPorId(idDesde);
	Vendedor hasta = this.obtenerPorId(idHasta);
	Set<Cliente> clientes = new HashSet<>(desde.getClientes());
	if (clientes.isEmpty())
	    throw new RuntimeException("No existen clientes para transferir");
	clientes.stream().forEach(cliente -> {
	    desde.removeCliente(cliente);
	    hasta.addCliente(cliente);
	    cliente.setVendedor(hasta);
	});
	actualizar(desde);
	actualizar(hasta);

    }
}
