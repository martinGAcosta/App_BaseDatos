package com.db.dbapp.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.dbapp.mappers.ItemMapper;
import com.db.dbapp.model.Carro;
import com.db.dbapp.model.CarroStatus;
import com.db.dbapp.model.Item;
import com.db.dbapp.model.Producto;
import com.db.dbapp.model.dtos.ItemDto;
import com.db.dbapp.repositories.CarroRepository;
import com.db.dbapp.repositories.ItemRepository;

@Service
public class CarroService implements IService<Carro> {

    private CarroRepository carroRepository;
    private ProductoService productoService;
    private ItemRepository itemRepository;

    @Autowired
    public CarroService(CarroRepository carroRepository, ProductoService productoService,
	    ItemRepository itemRepository) {
	this.carroRepository = carroRepository;
	this.productoService = productoService;
	this.itemRepository = itemRepository;
    }

    @Override
    public Carro obtenerPorId(Long id) {
	return carroRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("No se puede encontrar la compra con ID " + id));
    }

    @Override
    public Carro crear(Carro entity) {
	return carroRepository.save(entity);
    }

    @Override
    public void actualizar(Carro entity) {
	carroRepository.save(entity);

    }

    @Override
    public void eliminar(Long id) {
	this.cancelar(id);
    }

    @Override
    public List<Carro> listar() {
	return (List<Carro>) carroRepository.findAll();
    }

    public Carro obtenerCompraDeCliente(Long idCliente, Long idCompra) {
	return carroRepository.findByIdAndCliente_Id(idCompra, idCliente).orElseThrow(
		() -> new RuntimeException("No existe la compra " + idCompra + " del cliente " + idCliente));
    }

    @Transactional
    public void agregarItem(Long id, ItemDto itemDto) throws Throwable {
    	Item item = null;
		    	Producto producto = productoService.obtenerPorId(itemDto.getIdProducto());
			    if (producto.getStock().compareTo(itemDto.getCantidad()) < 0)
				throw new RuntimeException("La cantidad solicitada excede la disponibilidad del producto (queda "
					+ producto.getStock() + ")");
			    item = new Item(producto, producto.getPrecioUnitario(), itemDto.getCantidad());
				Carro carro = obtenerPorId(id);
				    if (CarroStatus.ACTIVO.equals(carro.getStatus())) {
				    	producto = item.getProducto();
				    	producto.setStock(producto.getStock().subtract(item.getCantidad()));
				    	productoService.actualizar(producto);
				    	item = itemRepository.save(item);
				    	carro.addItem(item);
				    	this.actualizar(carro);}
		}


    @Transactional
    public void cancelar(Long id) {
	Carro carro = obtenerPorId(id);
	carro.setStatus(CarroStatus.CANCELADO);
	carro.getItems().stream().forEach(item -> {
	    Producto producto;
	    try {
		producto = productoService.obtenerPorId(item.getProducto().getId());
		producto.setStock(producto.getStock().add(item.getCantidad()));
		productoService.actualizar(producto);
	    } catch (Throwable e) {
		e.printStackTrace();
		throw new RuntimeException("Hubo un problema al intentar recuperar un producto de la compra");
	    }
	});
	actualizar(carro);
    }

    @Transactional
    public void quitarItem(Long idCompra, Long id) {
	Carro carro = obtenerPorId(idCompra);
	Item it = obtenerItemDeCompra(id, carro);
	carro.removeItem(it);
	actualizar(carro);
	Producto producto = it.getProducto();
	producto.setStock(producto.getStock().add(it.getCantidad()));
	productoService.actualizar(producto);
	itemRepository.delete(it);
    }

    @Transactional
    public Item actualizarCantidadItem(Long idCompra, Long id, BigDecimal nuevaCantidad) {
	Carro carro = obtenerPorId(idCompra);
	Item it = obtenerItemDeCompra(id, carro);
	BigDecimal cantidadAnterior = it.getCantidad();
	Producto producto = it.getProducto();
	if (producto.getStock().add(cantidadAnterior).compareTo(nuevaCantidad) >= 0) {
	    producto.setStock(producto.getStock().add(it.getCantidad()).subtract(nuevaCantidad));
	    productoService.actualizar(producto);
	    it.setCantidad(nuevaCantidad);
	    itemRepository.save(it);
	    return it;
	}
	throw new RuntimeException("La cantidad actual no es suficiente para satisfacer el pedido");
    }

    private Item obtenerItemDeCompra(Long id, Carro carro) {
	return carro.getItems().stream().filter(item -> item.getId().compareTo(id) == 0).findAny()
		.orElseThrow(() -> new RuntimeException("El item con ID " + id + " no existe"));
    }
}
