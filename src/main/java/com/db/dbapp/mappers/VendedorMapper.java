package com.db.dbapp.mappers;

import com.db.dbapp.model.Vendedor;
import com.db.dbapp.model.dtos.VendedorDto;

public class VendedorMapper {

    public static Vendedor toVendedor(Vendedor vendedor, VendedorDto vendedorDto) {
	vendedor.setNombre(vendedorDto.getNombre());
	vendedor.setApellido(vendedorDto.getApellido());
	vendedor.setEmail(vendedorDto.getEmail());
	vendedor.setTelefono(vendedorDto.getTelefono());
	vendedor.setCodigo(vendedorDto.getCodigo());
	return vendedor;
    }

    public static VendedorDto toVendedorDto(VendedorDto vendedorDto, Vendedor vendedor) {
	if (vendedor != null) {
	    vendedorDto.setApellido(vendedor.getApellido());
	    vendedorDto.setNombre(vendedor.getNombre());
	    vendedorDto.setEmail(vendedor.getEmail());
	    vendedorDto.setTelefono(vendedor.getTelefono());
	    vendedorDto.setCodigo(vendedor.getCodigo());
	    vendedorDto.setId(vendedor.getId());
	}
	return vendedorDto;
    }

}
