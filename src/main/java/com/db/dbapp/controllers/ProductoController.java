package com.db.dbapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.db.dbapp.mappers.ProductoMapper;
import com.db.dbapp.model.Producto;
import com.db.dbapp.model.dtos.ProductoDto;
import com.db.dbapp.services.ProductoService;

@RestController
@RequestMapping(path = "administracion")
public class ProductoController {


    private ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService1) {
	this.productoService = productoService1;
    }

    @RequestMapping(path = "/productos/crear", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ProductoDto> crear(@RequestBody ProductoDto productoDto) {
	Producto producto = ProductoMapper.toProducto(new Producto(), productoDto);
	producto = productoService.crear(producto);
	productoDto = ProductoMapper.toProductoDto(productoDto, producto);
	return new ResponseEntity<ProductoDto>(productoDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/productos/actualizar/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<ProductoDto> actualizar(@PathVariable(name = "id") Long id,
	    @RequestBody ProductoDto productoDto) {
	try {
	    Producto producto = productoService.obtenerPorId(id);
	    producto = ProductoMapper.toProducto(producto, productoDto);
	    productoService.actualizar(producto);
	    productoDto = ProductoMapper.toProductoDto(productoDto, producto);
	    return new ResponseEntity<ProductoDto>(productoDto, HttpStatus.OK);

	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}
    }

    @RequestMapping(path = "/productos/borrar/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity borrar(@PathVariable(name = "id", required = true) Long id) {
	try {
	    productoService.eliminar(id);
	    return new ResponseEntity("Producto " + id + " eliminado", HttpStatus.OK);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

    }

    @RequestMapping(path = "/productos/listar", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ProductoDto>> listar() {
	List<Producto> productos = new ArrayList<>();
	List<ProductoDto> productosDto = new ArrayList<>();
	productos = productoService.listar();
	productosDto = productos.stream().map(v -> ProductoMapper.toProductoDto(new ProductoDto(), v))
		.collect(Collectors.toList());
	return new ResponseEntity<List<ProductoDto>>(productosDto, HttpStatus.OK);
    }
    
    @RequestMapping(path = "/productos/buscar/{descripcion}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ProductoDto>> buscar(@PathVariable(name = "descripcion") String descripcion) {
    List<Producto> productos = new ArrayList<>();
	List<ProductoDto> productosDto = new ArrayList<>();
	productos = productoService.buscar(descripcion);
	productosDto = productos.stream().map(v -> ProductoMapper.toProductoDto(new ProductoDto(), v))
		.collect(Collectors.toList());
	return new ResponseEntity<List<ProductoDto>>(productosDto, HttpStatus.OK);
    }

}
