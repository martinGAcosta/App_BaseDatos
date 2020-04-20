package com.db.dbapp.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.db.dbapp.mappers.CarroMapper;
import com.db.dbapp.mappers.ClienteMapper;
import com.db.dbapp.model.Carro;
import com.db.dbapp.model.Cliente;
import com.db.dbapp.model.Persona;
import com.db.dbapp.model.Vendedor;
import com.db.dbapp.model.dtos.CarroDto;
import com.db.dbapp.model.dtos.ClienteDto;
import com.db.dbapp.services.CarroService;
import com.db.dbapp.services.ClienteService;
import com.db.dbapp.services.ProductoService;
import com.db.dbapp.services.VendedorService;

@RestController
@RequestMapping("vendedor")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class VentaController extends CarroController {

    private VendedorService vendedorService;
    private CarroService carroService;
    private ClienteService clienteService;

    @Autowired
    public VentaController(VendedorService vendedorService, CarroService carroService, ClienteService clienteService,
	    ProductoService productoService) {
	super(carroService, productoService);
	this.vendedorService = vendedorService;
	this.carroService = carroService;
	this.clienteService = clienteService;
    }

    @GetMapping(path = "cliente/{id}/{idCliente}")
    public ResponseEntity<ClienteDto> verCliente(@PathVariable(name = "id") Long id,
	    @PathVariable(name = "idCliente") Long idCliente) {
	Cliente cliente = null;
	try {
	    cliente = obtenerCliente(id, idCliente);
	} catch (Exception e) {
	    e.printStackTrace();
	    new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	} catch (Throwable e) {
	    e.printStackTrace();
	    new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

	return new ResponseEntity<ClienteDto>(ClienteMapper.toClienteDto(new ClienteDto(), cliente, null),
		HttpStatus.FOUND);
    }

//
    @RequestMapping(path = "{idVendedor}/compras/{idCliente}", method = RequestMethod.GET)
    public ResponseEntity<Collection<CarroDto>> verComprasCliente(@PathVariable(name = "idVendedor") Long idVendedor,
	    @PathVariable(name = "idCliente") Long idCliente) {
	Cliente cliente = null;
	try {
	    cliente = obtenerCliente(idVendedor, idCliente);
	} catch (Exception e) {
	    e.printStackTrace();
	    new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	} catch (Throwable e) {
	    e.printStackTrace();
	    new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

	Collection<CarroDto> compras = cliente.getCompras().stream().map(c -> CarroMapper.toCarroDto(new CarroDto(), c))
		.collect(Collectors.toSet());
	return new ResponseEntity<Collection<CarroDto>>(compras, HttpStatus.OK);
    }

    @RequestMapping(path = "ventas/{id}", method = RequestMethod.GET)
    public ResponseEntity<Collection<CarroDto>> verVentas(@PathVariable(name = "id") Long idVendedor) {
	Vendedor vendedor = null;
	try {
	    vendedor = vendedorService.obtenerPorId(idVendedor);
	} catch (Exception e) {
	    e.printStackTrace();
	    new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	} catch (Throwable e) {
	    e.printStackTrace();
	    new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

	Collection<CarroDto> compras = vendedor.getVentas().stream().map(c -> CarroMapper.toCarroDto(new CarroDto(), c))
		.collect(Collectors.toSet());

	return new ResponseEntity<Collection<CarroDto>>(compras, HttpStatus.OK);
    }

    @RequestMapping(path = "crear/{idVendedor}/{idCliente}", method = RequestMethod.POST)
    public ResponseEntity<CarroDto> crearVenta(@PathVariable(name = "idVendedor") Long idVendedor,
	    @PathVariable(name = "idCliente") Long idCliente) {
	Vendedor vendedor = null;
	Cliente cliente = null;
	CarroDto ventaDto = null;
	try {
	    vendedor = vendedorService.obtenerPorId(idVendedor);
	    cliente = clienteService.obtenerPorId(idCliente);

	    Carro venta = new Carro();
	    venta.setCliente(cliente);
	    venta.setVendedor(vendedor);
	    venta.setUsuario((Persona) vendedor);
	    vendedor.getVentas().add(venta);
	    cliente.getCompras().add(venta);
	    carroService.crear(venta);
	    vendedorService.actualizar(vendedor);
	    clienteService.actualizar(cliente);
	    ventaDto = CarroMapper.toCarroDto(new CarroDto(), venta);
	} catch (Exception e) {
	    e.printStackTrace();
	    new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	} catch (Throwable e) {
	    e.printStackTrace();
	    new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

	return new ResponseEntity<CarroDto>(ventaDto, HttpStatus.OK);
    }

    private Cliente obtenerCliente(Long idVendedor, Long idCliente) throws Throwable {
	Cliente cliente;
	Vendedor vendedor = vendedorService.obtenerPorId(idVendedor);

	cliente = vendedor.getClientes().stream().filter(c -> c.getId().compareTo(idCliente) == 0).findFirst()
		.orElseThrow(() -> new RuntimeException("El cliente no fue encontrado"));
	return cliente;
    }

}
