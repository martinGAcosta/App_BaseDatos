package com.db.dbapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.db.dbapp.mappers.CarroMapper;
import com.db.dbapp.mappers.ProductoMapper;
import com.db.dbapp.model.Carro;
import com.db.dbapp.model.Cliente;
import com.db.dbapp.model.Persona;
import com.db.dbapp.model.Producto;
import com.db.dbapp.model.dtos.CarroDto;
import com.db.dbapp.model.dtos.ProductoDto;
import com.db.dbapp.services.CarroService;
import com.db.dbapp.services.ClienteService;
import com.db.dbapp.services.ProductoService;

@RestController
@RequestMapping("compras")
@SuppressWarnings("rawtypes")
public class CompraController extends CarroController {

    private CarroService carroService;
    private ClienteService clienteService;

    @Autowired
    public CompraController(CarroService carroService, ClienteService clienteService, ProductoService productoService) {
	super(carroService, productoService);
	this.carroService = carroService;
	this.clienteService = clienteService;
    }

    @PostMapping("crear/{idCliente}")
    public ResponseEntity<CarroDto> crearCompra(@PathVariable(name = "idCliente", required = true) Long idCliente) {
	try {
	    Cliente cliente = clienteService.obtenerPorId(idCliente);
	    if (cliente.getVendedor() == null)
		return new ResponseEntity("No puede comprar. No tiene un vendedor asignado", HttpStatus.BAD_REQUEST);
	    Carro carro = new Carro();
	    carro.setCliente(cliente);
	    carro.setUsuario((Persona) cliente);
	    carroService.crear(carro);
	    CarroDto carroDto = CarroMapper.toCarroDto(new CarroDto(), carro);
	    return new ResponseEntity<CarroDto>(carroDto, HttpStatus.CREATED);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity("El cliente con id " + idCliente + " no existe", HttpStatus.BAD_REQUEST);
	}
    }
    
    @RequestMapping(path = "/listar", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<CarroDto>> listar() {
	List<Carro> carros = new ArrayList<>();
	List<CarroDto> carrosDto = new ArrayList<>();
	carros = carroService.listar();
	carrosDto = carros.stream().map(v -> CarroMapper.toCarroDto(new CarroDto(), v))
		.collect(Collectors.toList());
	return new ResponseEntity<List<CarroDto>>(carrosDto, HttpStatus.OK);
    }

}
