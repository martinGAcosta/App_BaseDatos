package com.db.dbapp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Entity
public abstract class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telefono = "";
    @Email
    private String email = "";
    private String apellido = "";
    private String nombre = "";

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

    public Long getId() {
	return id;
    }

    @Override
    public String toString() {
	return apellido + ", " + nombre;
    }

}
