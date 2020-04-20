package com.db.dbapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dbapp.model.Admin;
import com.db.dbapp.repositories.AdminRepository;

@Service
public class AdminService extends PersonaService<Admin, Long> {

    AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
	this.adminRepository = adminRepository;
    }

    @Override
    public Admin crear(Admin entity) {
	return adminRepository.save(entity);
    }

    @Override
    public void actualizar(Admin entity) {
	adminRepository.save(entity);

    }

    @Override
    public void eliminar(Long id) {
	try {
	    Admin admin = this.obtenerPorId(id);
	    adminRepository.delete(admin);
	} catch (Throwable e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getLocalizedMessage());
	}
    }

    @Override
    public List<Admin> listar() {
	return (List<Admin>) adminRepository.findAll();
    }

    @Override
    public Admin obtenerPorId(Long id) throws Throwable {
	Object o = super.obtenerPorId(id);
	try {
	    return (Admin) o;
	} catch (ClassCastException e) {
	    throw new RuntimeException("El id " + id + " no corresponde a un admin valido");
	}
    }

}
