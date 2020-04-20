package com.db.dbapp.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.db.dbapp.mappers.ClienteMapper;
import com.db.dbapp.model.Carro;
import com.db.dbapp.model.Cliente;
import com.db.dbapp.model.Vendedor;
import com.db.dbapp.model.dtos.ClienteDto;
import com.db.dbapp.services.ClienteService;
import com.db.dbapp.services.VendedorService;

@RestController
@RequestMapping("administracion")
@SuppressWarnings("rawtypes")
public class AdminClienteController {

    private ClienteService clienteService;
    private VendedorService vendedorService;

    @Autowired
    public AdminClienteController(ClienteService clienteService, VendedorService vendedorService) {
	this.clienteService = clienteService;
	this.vendedorService = vendedorService;
    }

    @RequestMapping(path = "/clientes/crear/{idVendedor}", method = RequestMethod.POST, produces = "Application/json")
    public ResponseEntity<ClienteDto> crearCliente(@PathVariable(name = "idVendedor", required = true) Long idVendedor,
	    @RequestBody ClienteDto clienteDto) {
	try {
	    Vendedor vendedor = vendedorService.obtenerPorId(idVendedor);
	    Cliente cliente = ClienteMapper.toCliente(new Cliente(), new HashSet<Carro>(), clienteDto);
	    cliente.setVendedor(vendedor);
	    cliente = clienteService.crear(cliente);
	    vendedor.addCliente(cliente);
	    vendedorService.actualizar(vendedor);
	    clienteDto = ClienteMapper.toClienteDto(clienteDto, cliente, null);
	    return new ResponseEntity<ClienteDto>(clienteDto, HttpStatus.CREATED);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}
    }

    @RequestMapping(path = "/clientes/actualizar/{id}", method = RequestMethod.PUT, produces = "Application/json")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable(name = "id", required = true) Long id,
	    @RequestBody ClienteDto clienteDto) {
	try {
	    Cliente cliente = clienteService.obtenerPorId(id);
	    cliente = ClienteMapper.toCliente(cliente, cliente.getCompras(), clienteDto);
	    clienteService.actualizar(cliente);
	    clienteDto = ClienteMapper.toClienteDto(clienteDto, cliente, null);
	    return new ResponseEntity<ClienteDto>(clienteDto, HttpStatus.OK);

	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.OK);
	}
    }

    @RequestMapping(path = "/clientes/borrar/{id}", method = RequestMethod.DELETE, produces = "Application/json")
    public ResponseEntity borrarCliente(@PathVariable(name = "id", required = true) Long id) {
	try {
		Cliente cliente = clienteService.obtenerPorId(id);
		if (cliente.getCompras() != null) {
		    return new ResponseEntity("El Cliente " + id + " no puede ser eliminado por tener compras creadas", HttpStatus.OK);
		}
		else
			{
			if (cliente.getVendedor() != null) {
				Vendedor vendedor = vendedorService.obtenerPorId(cliente.getVendedor().getId());
				vendedor.removeCliente(cliente);
				vendedorService.actualizar(vendedor);}
				clienteService.eliminar(id);
			    return new ResponseEntity("Cliente " + id + " eliminado", HttpStatus.OK);
			}
		} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}
    }

    @RequestMapping(path = "/clientes/listar", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ClienteDto>> listarClientes() {
	List<ClienteDto> clientesDto = new ArrayList<>();
	clientesDto = new ArrayList<ClienteDto>(ClienteMapper.toClienteDtoSet(new HashSet(clienteService.listar())));

	return new ResponseEntity<List<ClienteDto>>(clientesDto, HttpStatus.OK);
    }

    @RequestMapping(path = "/clientes/reasignar/{idCliente}/{idVendedor}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ClienteDto> reasignarVendedor(
	    @PathVariable(name = "idCliente", required = true) Long idCliente,
	    @PathVariable(name = "idVendedor", required = true) Long idVendedor) {

	try {
	    Cliente cliente = clienteService.obtenerPorId(idCliente);
	    Vendedor vendedor = vendedorService.obtenerPorId(idVendedor);
	    if (cliente.getVendedor() != null) {
		cliente.getVendedor().removeCliente(cliente);
	    }
	    cliente.setVendedor(vendedor);
	    vendedor.addCliente(cliente);
	    clienteService.actualizar(cliente);
	    vendedorService.actualizar(vendedor);

	    ClienteDto clienteDto = ClienteMapper.toClienteDto(new ClienteDto(), cliente, null);
	    return new ResponseEntity(clienteDto, HttpStatus.OK);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

    }

    @RequestMapping(path = "clientes/quitarVendedor/{id}", method = RequestMethod.POST)
    public ResponseEntity quitarVendedor(@PathVariable(name = "id") Long id) {
	try {
	    Cliente cliente = clienteService.obtenerPorId(id);
	    Vendedor vendedor = cliente.getVendedor();
	    if (vendedor != null) {
		vendedor.removeCliente(cliente);
		cliente.setVendedor(null);
		vendedorService.actualizar(vendedor);
		clienteService.actualizar(cliente);
	    }
	    return new ResponseEntity("El cliente fue desvinculado del vendedor", HttpStatus.OK);
	} catch (Throwable e) {
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}
    }
}
