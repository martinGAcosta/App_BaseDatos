package com.db.dbapp.model.dtos;

import java.util.HashSet;
import java.util.Set;

public class ClienteDto extends PersonaDto {

    private Long dni = null;
    private VendedorDto vendedor = null;
    private Set<CarroDto> compras = new HashSet<>();

    public Long getDni() {
	return dni;
    }

    public void setDni(Long dni) {
	this.dni = dni;
    }

    public VendedorDto getVendedor() {
	return vendedor;
    }

    public void setVendedor(VendedorDto vendedor) {
	this.vendedor = vendedor;
    }

    public Set<CarroDto> getCompras() {
	return compras;
    }

    public void setCompras(Set<CarroDto> compras) {
	this.compras = compras;
    }

}
