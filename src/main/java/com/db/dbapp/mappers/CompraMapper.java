package com.db.dbapp.mappers;

import com.db.dbapp.model.Compra;
import com.db.dbapp.model.dtos.CompraDto;

public class CompraMapper {

    public static Compra toCompra(Compra compra, CompraDto compraDto) {
	compra.setFecha(compraDto.getFecha());
	return compra;
    }

    public static CompraDto toCompraDto(CompraDto compraDto, Compra compra) {
	compraDto.setFecha(compra.getFecha());
	return compraDto;
    }

}
