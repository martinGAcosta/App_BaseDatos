package com.db.dbapp.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.db.dbapp.mappers.ClienteMapper;
import com.db.dbapp.mappers.VendedorMapper;
import com.db.dbapp.model.Cliente;
import com.db.dbapp.model.Vendedor;
import com.db.dbapp.model.dtos.ClienteDto;
import com.db.dbapp.model.dtos.VendedorDto;
import com.db.dbapp.services.ClienteService;
import com.db.dbapp.services.VendedorService;

@RestController
@RequestMapping(path = "administracion")
public class AdminVendedorController {

    private VendedorService vendedorService;
    private ClienteService clienteService;

    @Autowired
    public AdminVendedorController(VendedorService vendedorService, ClienteService clienteService) {
	this.vendedorService = vendedorService;
	this.clienteService = clienteService;
    }

    @RequestMapping(path = "/vendedores/crear", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<VendedorDto> crear(@RequestBody VendedorDto vendedorDto) {
	Vendedor vendedor = VendedorMapper.toVendedor(new Vendedor(), vendedorDto);
	vendedor = vendedorService.crear(vendedor);
	vendedorDto = VendedorMapper.toVendedorDto(vendedorDto, vendedor);
	return new ResponseEntity<VendedorDto>(vendedorDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/vendedores/actualizar/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<VendedorDto> actualizar(@PathVariable(name = "id") Long id,
	    @RequestBody VendedorDto vendedorDto) {
	try {
	    Vendedor vendedor = vendedorService.obtenerPorId(id);
	    vendedor = VendedorMapper.toVendedor(vendedor, vendedorDto);
	    vendedorService.actualizar(vendedor);
	    vendedorDto = VendedorMapper.toVendedorDto(vendedorDto, vendedor);
	    return new ResponseEntity<VendedorDto>(vendedorDto, HttpStatus.OK);

	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.OK);
	}
    }

    @RequestMapping(path = "/vendedores/borrar/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity borrar(@PathVariable(name = "id", required = true) Long id) {
	try {
	    vendedorService.eliminar(id);
	    return new ResponseEntity("Vendedor " + id + " eliminado", HttpStatus.OK);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

    }

    @RequestMapping(path = "/vendedores/listar", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<VendedorDto>> listar() {
	List<Vendedor> vendedores = new ArrayList<>();
	List<VendedorDto> vendedoresDto = new ArrayList<>();
	vendedores = vendedorService.listar();
	vendedoresDto = vendedores.stream().map(v -> VendedorMapper.toVendedorDto(new VendedorDto(), v))
		.collect(Collectors.toList());
	return new ResponseEntity<List<VendedorDto>>(vendedoresDto, HttpStatus.OK);
    }

    @RequestMapping(path = "/vendedores/{id}/clientes", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Set<ClienteDto>> listarClientes(@PathVariable(name = "id", required = true) Long id) {
	List<Cliente> clientes = new ArrayList<>();

	try {
	    Vendedor vendedor = vendedorService.obtenerPorId(id);
	    Set<ClienteDto> clientesDto = new HashSet<>();
	    clientesDto = (Set<ClienteDto>) ClienteMapper.toClienteDtoSet(vendedor.getClientes());
	    return new ResponseEntity<Set<ClienteDto>>(clientesDto, HttpStatus.OK);

	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.OK);
	}

    }

    	

    @RequestMapping(path = "/vendedores/transferir/{idDesde}/{idHasta}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<VendedorDto> transferirCliente(@PathVariable(name = "idDesde", required = true) Long idDesde,
	    @PathVariable(name = "idHasta", required = true) Long idHasta) {

	try {
	    Vendedor desde = vendedorService.obtenerPorId(idDesde);
	    Vendedor hasta = vendedorService.obtenerPorId(idHasta);
	    Set<Cliente> clientes = new HashSet<>(desde.getClientes());
	    if (clientes.isEmpty()) {
	    	return new ResponseEntity(
	    		    "No existen clientes para transferir", HttpStatus.OK);} else {
	    clientes.stream().forEach(cliente -> {
		desde.removeCliente(cliente);
		hasta.addCliente(cliente);
		cliente.setVendedor(hasta);
	    });
	    vendedorService.actualizar(desde);
	    vendedorService.actualizar(hasta);
	    return new ResponseEntity(
		    "Los clientes fueron transferidos del vendedor " + idDesde + " al vendedor " + idHasta,
		    HttpStatus.OK);}

	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

    }
}
