package com.db.dbapp.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Persona {
    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private Long dni;
    @ManyToOne(fetch = FetchType.EAGER)
    private Vendedor vendedor;
    @OneToMany
    @JoinTable(name = "Cliente_Compras")
    private Set<Carro> carros = new HashSet<>();

    public Cliente() {
	// TODO Auto-generated constructor stub
    }

    public Long getDni() {
	return dni;
    }

    public void setDni(Long dni) {
	this.dni = dni;
    }

    public Vendedor getVendedor() {
	return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
	this.vendedor = vendedor;
    }

    public BigDecimal getMontoCompradoPeriodo(Date desde, Date hasta) {
	if (carros == null || carros.isEmpty())
	    return BigDecimal.ZERO;
	return carros.stream().filter(c -> desde.after(c.getFecha()) && hasta.before(c.getFecha()))
		.map(c -> c.getMonto()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<Carro> getCompras() {
	return carros;
    }

    public void setCompras(Set<Carro> carros) {
	this.carros = carros;
    }

}
