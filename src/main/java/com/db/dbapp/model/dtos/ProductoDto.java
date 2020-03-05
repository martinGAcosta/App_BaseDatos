package com.db.dbapp.model.dtos;

import java.math.BigDecimal;

public class ProductoDto {

    private Long id;
    private String codigo = "";
    private String descripcion = "";
    private BigDecimal precioUnitario = BigDecimal.ZERO;
    private BigDecimal stock = BigDecimal.ZERO;

    public ProductoDto() {

    }

    public ProductoDto(Long id, String codigo, String descripcion, BigDecimal precioUnitario, BigDecimal stock) {
	super();
	this.id = id;
	this.codigo = codigo;
	this.descripcion = descripcion;
	this.precioUnitario = precioUnitario;
	this.stock = stock;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getCodigo() {
	return codigo;
    }

    public void setCodigo(String codigo) {
	this.codigo = codigo;
    }

    public String getDescripcion() {
	return descripcion;
    }

    public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
    }

    public BigDecimal getPrecioUnitario() {
	return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
	this.precioUnitario = precioUnitario;
    }

    public BigDecimal getStock() {
	return stock;
    }

    public void setStock(BigDecimal stock) {
	this.stock = stock;
    }

}
