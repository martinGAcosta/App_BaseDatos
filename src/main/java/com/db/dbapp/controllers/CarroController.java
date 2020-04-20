package com.db.dbapp.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.db.dbapp.mappers.CarroMapper;
import com.db.dbapp.mappers.ItemMapper;
import com.db.dbapp.model.Carro;
import com.db.dbapp.model.CarroStatus;
import com.db.dbapp.model.Item;
import com.db.dbapp.model.Producto;
import com.db.dbapp.model.dtos.CarroDto;
import com.db.dbapp.model.dtos.ItemDto;
import com.db.dbapp.services.CarroService;
import com.db.dbapp.services.ProductoService;

@RestController
public abstract class CarroController {
    private CarroService carroService;
    private ProductoService productoService;

    @Autowired
    public CarroController(CarroService carroService, ProductoService productoService) {
	this.carroService = carroService;
	this.productoService = productoService;
    }

    @PostMapping("agregarItem/{id}")
    public ResponseEntity<ItemDto> agregarItemCompra(@PathVariable(name = "id", required = true) Long id,
	    @RequestBody ItemDto itemDto) {
	Item item = null;
	Producto producto = null;
	try {
	    producto = productoService.obtenerPorId(itemDto.getIdProducto());
	    if (producto.getStock().compareTo(itemDto.getCantidad()) < 0)
		throw new RuntimeException("La cantidad solicitada excede la disponibilidad del producto (queda "
			+ producto.getStock() + ")");
	    item = new Item(producto, producto.getPrecioUnitario(), itemDto.getCantidad());

	} catch (Exception e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

	try {
		 Carro carro = carroService.obtenerPorId(id);
		    if (CarroStatus.ACTIVO.equals(carro.getStatus())) {
		 		carroService.agregarItem(id, item);
	    itemDto = ItemMapper.toItemDto(new ItemDto(), item);
	    return new ResponseEntity<ItemDto>(itemDto, HttpStatus.OK);
		    }
		    else
			    return new ResponseEntity("El item no puede agregarse porque el carro ya no esta activo", HttpStatus.BAD_REQUEST);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}
    }

//
    @PostMapping("actualizarCantidad/{idCompra}/{idItem}/{cantidad}")
    public ResponseEntity<ItemDto> actualizarCantidadItem(
	    @PathVariable(name = "idCompra", required = true) Long idCompra, @PathVariable(name = "idItem", 
	    required = true) Long idItem, @PathVariable(name = "cantidad", required = true) BigDecimal cantidad) {
	try {
		 Carro carro = carroService.obtenerPorId(idCompra);
		 if (CarroStatus.ACTIVO.equals(carro.getStatus())) {
			 Item itemAux = carroService.actualizarCantidadItem(idCompra, idItem, cantidad);
			    return new ResponseEntity("El item se actualizo correctamente", HttpStatus.BAD_REQUEST);
		 }
		    else
			    return new ResponseEntity("El item no puede actualizarse porque el carro ya no esta activo", HttpStatus.BAD_REQUEST);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

    }

//
    @PostMapping("quitarItem/{idCompra}/{idItem}")
    public ResponseEntity<String> quitarItem(@PathVariable(name = "idCompra", required = true) Long idCompra,
	    @PathVariable(name = "idItem", required = true) Long idItem) {
	try {
	    Carro carro = carroService.obtenerPorId(idCompra);
	    if (CarroStatus.ACTIVO.equals(carro.getStatus())) {
	    carroService.quitarItem(idCompra, idItem);
	    return new ResponseEntity<String>("El item fue quitado", HttpStatus.OK);
	    }
	    else
		    return new ResponseEntity<String>("El item no puede quitarse porque el carro no esta activo", HttpStatus.OK);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

    }

//
    @PostMapping("cancelar/{id}")
    public ResponseEntity<String> cancelarCompra(@PathVariable(name = "id", required = true) Long id) {
	try {
	    carroService.eliminar(id);
	    return new ResponseEntity<String>("La compra fue cancelada", HttpStatus.OK);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}
    }

    @PostMapping("confirmar/{id}")
    public ResponseEntity<String> confirmarCompra(@PathVariable(name = "id", required = true) Long id) {
	try {
	    Carro carro = carroService.obtenerPorId(id);
	    if (CarroStatus.ACTIVO.equals(carro.getStatus()) && !carro.getItems().isEmpty()) {
		carro.setStatus(CarroStatus.CONFIRMADO);
		carroService.actualizar(carro);
		return new ResponseEntity<String>("La compra fue confirmada", HttpStatus.OK);
	    } else
	    { if (CarroStatus.ACTIVO.equals(carro.getStatus()))
	    	return new ResponseEntity<String>(
	    			"La compra no tiene items, lo cual no se puede confirmar",
	    			HttpStatus.OK);
	    	else
		return new ResponseEntity<String>(
			"El status de la compra es " + carro.getStatus().getStatus() + " y no puede confirmarse.",
			HttpStatus.OK);}
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

    }

    @GetMapping("ver/{id}")
    public ResponseEntity<CarroDto> verCompra(@PathVariable(name = "id", required = true) Long id) {
	try {
	    Carro carro = carroService.obtenerPorId(id);
	    CarroDto carroDto = CarroMapper.toCarroDto(new CarroDto(), carro);
	    carroDto.setItems(ItemMapper.toItemDtoList(carro.getItems()));
	    return new ResponseEntity<CarroDto>(carroDto, HttpStatus.OK);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

    }

}
