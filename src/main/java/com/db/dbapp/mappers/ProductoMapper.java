package com.db.dbapp.mappers;

import com.db.dbapp.model.Producto;
import com.db.dbapp.model.dtos.ProductoDto;

public class ProductoMapper {

    private ProductoMapper instance;

    private ProductoMapper() {
	instance = new ProductoMapper();
    }

    public static Producto toProducto(Producto producto, ProductoDto productoDto) {
	producto.setCodigo(productoDto.getCodigo());
	producto.setDescripcion(productoDto.getDescripcion());
	producto.setPrecioUnitario(productoDto.getPrecioUnitario());
	producto.setStock(productoDto.getStock());
	return producto;
    }

    public static ProductoDto toProductoDto(ProductoDto productoDto,Producto producto) {
    	productoDto.setCodigo(producto.getCodigo());
    	productoDto.setDescripcion(producto.getDescripcion());
    	productoDto.setPrecioUnitario(producto.getPrecioUnitario());
    	productoDto.setStock(producto.getStock());
    	productoDto.setId(producto.getId());
    	return productoDto;
    }

    public ProductoMapper getInstance() {
	return instance;
    }

   
}
