package com.db.dbapp.model.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarroDto {

    private Long id = null;
    private Date fecha = new Date();
    //private PersonaDto usuario;
    //private VendedorDto vendedor;
    private ClienteDto cliente;
    private String status;

    private List<ItemDto> items = new ArrayList<>();

    public CarroDto() {
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

//    public PersonaDto getUsuario() {
//	return usuario;
//    }
//
//    public void setUsuario(PersonaDto usuario) {
//	this.usuario = usuario;
//    }
//
//    public VendedorDto getVendedor() {
//	return vendedor;
//    }
//
//    public void setVendedor(VendedorDto vendedor) {
//	this.vendedor = vendedor;
//    }
//
    public ClienteDto getCliente() {
	return cliente;
    }

    public void setCliente(ClienteDto cliente) {
	this.cliente = cliente;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

}
