package com.db.dbapp.model.dtos;

import java.math.BigDecimal;

public class ItemDto {

    private Long id = null;
    private Long idProducto = null;
    private String producto = "";
    private String codigoProducto = "";
    private BigDecimal cantidad = BigDecimal.ONE;
    private BigDecimal precioUnitario = BigDecimal.ZERO;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getIdProducto() {
	return idProducto;
    }

    public void setIdProducto(Long idProducto) {
	this.idProducto = idProducto;
    }

    public String getProducto() {
	return producto;
    }

    public void setProducto(String producto) {
	this.producto = producto;
    }

    public String getCodigoProducto() {
	return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
	this.codigoProducto = codigoProducto;
    }

    public BigDecimal getCantidad() {
	return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
	this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
	return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
	this.precioUnitario = precioUnitario;
    }

}
