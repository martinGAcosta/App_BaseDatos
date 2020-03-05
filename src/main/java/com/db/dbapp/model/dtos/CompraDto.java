package com.db.dbapp.model.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompraDto {

    private Long id = null;
    private Date fecha = new Date();
    private List<ItemDto> items = new ArrayList<>();

    public CompraDto() {
	// TODO Auto-generated constructor stub
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getFecha() {
	return fecha;
    }

    public void setFecha(Date fecha) {
	this.fecha = fecha;
    }

    public List<ItemDto> getItems() {
	return items;
    }

    public void setItems(List<ItemDto> items) {
	this.items = items;
    }

}
