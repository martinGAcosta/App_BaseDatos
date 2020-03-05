package com.db.dbapp.model.dtos;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class VendedorDto extends PersonaDto {

    private String codigo = "";
    private BigDecimal comision = BigDecimal.ZERO;
    private Set<ClienteDto> clientes = new HashSet<ClienteDto>();

    public String getCodigo() {
	return codigo;
    }

    public void setCodigo(String codigo) {
	this.codigo = codigo;
    }

    public BigDecimal getComision() {
	return comision;
    }

    public void setComision(BigDecimal comision) {
	this.comision = comision;
    }

    public Set<ClienteDto> getClientes() {
	return clientes;
    }

    public void setClientes(Set<ClienteDto> clientes) {
	this.clientes = clientes;
    }

}
