package com.db.dbapp.model.dtos;

public class PersonaDto {
    private Long id = null;
    private String nombre = "";
    private String apellido = "";
    private String telefono = "";
    private String email = "";

    public PersonaDto() {
	// TODO Auto-generated constructor stub
    }

    public PersonaDto(Long id, String nombre, String apellido, String telefono, String email) {
	this.id = id;
	this.nombre = nombre;
	this.apellido = apellido;
	this.telefono = telefono;
	this.email = email;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getTelefono() {
	return telefono;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getApellido() {
	return apellido;
    }

    public void setApellido(String apellido) {
	this.apellido = apellido;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

}
