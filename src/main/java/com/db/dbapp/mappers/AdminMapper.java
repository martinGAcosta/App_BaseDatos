package com.db.dbapp.mappers;

import com.db.dbapp.model.Admin;
import com.db.dbapp.model.Vendedor;
import com.db.dbapp.model.dtos.AdminDto;
import com.db.dbapp.model.dtos.VendedorDto;

public class AdminMapper {

    public static Admin toAdmin(Admin admin, AdminDto adminDto) {
	admin.setNombre(adminDto.getNombre());
	admin.setApellido(adminDto.getApellido());
	admin.setEmail(adminDto.getEmail());
	admin.setTelefono(adminDto.getTelefono());
	return admin;
    }

    public static AdminDto toAdminDto(AdminDto adminDto, Admin admin) {
	if (admin != null) {
	    adminDto.setApellido(admin.getApellido());
	    adminDto.setNombre(admin.getNombre());
	    adminDto.setEmail(admin.getEmail());
	    adminDto.setTelefono(admin.getTelefono());
	    adminDto.setId(admin.getId());
	}
	return adminDto;
    }

}
