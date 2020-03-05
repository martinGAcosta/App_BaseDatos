package com.db.dbapp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fecha = new Date();
    @OneToMany
    private List<Item> items = new ArrayList<>();

    public Compra() {
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
}
