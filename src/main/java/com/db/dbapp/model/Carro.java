package com.db.dbapp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Carro implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fecha = new Date();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private CarroStatus status = CarroStatus.ACTIVO;

    @ManyToOne
    private Vendedor vendedor;

    @ManyToOne
    private Persona usuario;

    @ManyToOne
    private Cliente cliente;

    public Carro() {
	// TODO Auto-generated constructor stub
    }

    public Date getFecha() {
	return fecha;
    }

    public void setFecha(Date fecha) {
	this.fecha = fecha;
    }

    public List<Item> getItems() {
	return items;
    }

    public void setItems(List<Item> items) {
	this.items = items;
    }

    public Long getId() {
	return id;
    }

    public BigDecimal getMonto() {
	if (items == null || items.isEmpty())
	    return BigDecimal.ZERO;
	return items.stream().map(i -> i.getMonto()).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public Vendedor getVendedor() {
	return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
	this.vendedor = vendedor;
    }

    public Persona getUsuario() {
	return usuario;
    }

    public void setUsuario(Persona usuario) {
	this.usuario = usuario;
    }

    public Cliente getCliente() {
	return cliente;
    }

    public void setCliente(Cliente cliente) {
	this.cliente = cliente;
    }

    public void addItem(Item item) {
	getItems().add(item);
    }

    public void removeItem(Item item) {
	getItems().remove(item);
    }

    public CarroStatus getStatus() {
	return status;
    }

    public void setStatus(CarroStatus status) {
	this.status = status;
    }

}
