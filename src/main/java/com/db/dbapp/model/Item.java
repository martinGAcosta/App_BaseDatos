package com.db.dbapp.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal cantidad = BigDecimal.ONE;
    private BigDecimal precioUnitario = BigDecimal.ZERO;
    @OneToOne
    private Producto producto;

    public Item() {
	// TODO Auto-generated constructor stub
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

    public Producto getProducto() {
	return producto;
    }

    public void setProducto(Producto producto) {
	this.producto = producto;
    }

    public Long getId() {
	return id;
    }

    public BigDecimal getMonto() {
	if (cantidad == null || precioUnitario == null)
	    return BigDecimal.ZERO;
	return cantidad.multiply(precioUnitario);
    }

}
