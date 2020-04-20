package com.db.dbapp.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("VENDEDOR")
public class Vendedor extends Persona {
    private static final long serialVersionUID = 1L;

    private String codigo = "";
    private BigDecimal comision = BigDecimal.ZERO;

    @JoinTable(name = "Vendedor_Clientes")
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Cliente> clientes = new HashSet<Cliente>();

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Carro> ventas = new HashSet<>();

    public Vendedor() {
	// TODO Auto-generated constructor stub
    }

    public String getCodigo() {
	return codigo;
    }

    public void setCodigo(String codigo) {
	this.codigo = codigo;
    }

    public BigDecimal getComision() {
	return comision;
    }

    public void setComision(BigDecimal comision) {
	this.comision = comision;
    }

    public Set<Cliente> getClientes() {
	return clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
	this.clientes = clientes;
    }

    public BigDecimal getMontoFacturadoPeriodo(Date desde, Date hasta) {
	if (clientes == null || clientes.isEmpty())
	    return BigDecimal.ZERO;
	return clientes.stream().map(c -> c.getMontoCompradoPeriodo(desde, hasta)).reduce(BigDecimal.ZERO,
		BigDecimal::add);
    }

    public void addCliente(Cliente cliente) {
	clientes.add(cliente);

    }

    public void removeCliente(Cliente cli) {
	clientes.remove(cli);

    }

    public Set<Carro> getVentas() {
	return ventas;
    }

    public void setVentas(Set<Carro> ventas) {
	this.ventas = ventas;
    }

    public BigDecimal getMontoCompradoPeriodo(Date desde, Date hasta) {
	if (ventas == null || ventas.isEmpty())
	    return BigDecimal.ZERO;
	return ventas.stream().filter(c -> desde.after(c.getFecha()) && hasta.before(c.getFecha()))
		.map(c -> c.getMonto()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
