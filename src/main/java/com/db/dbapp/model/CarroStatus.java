package com.db.dbapp.model;

public enum CarroStatus {
    ACTIVO("Activo"), CANCELADO("Cancelado"), CONFIRMADO("Confirmado");

    private String status;

    private CarroStatus(String status) {
	this.setStatus(status);
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }
}
