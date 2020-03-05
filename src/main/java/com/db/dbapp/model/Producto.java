package com.db.dbapp.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo = "";
    private String descripcion = "";
    private BigDecimal precioUnitario = BigDecimal.ZERO;
    private BigDecimal stock = BigDecimal.ZERO;

    public Producto() {
	// TODO Auto-generated constructor stub
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

    public Long getId() {
	return id;
    }

}
