package com.db.dbapp.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.db.dbapp.mappers.AdminMapper;
import com.db.dbapp.mappers.ProductoMapper;
import com.db.dbapp.mappers.VendedorMapper;
import com.db.dbapp.model.Admin;
import com.db.dbapp.model.Producto;
import com.db.dbapp.model.Vendedor;
import com.db.dbapp.model.dtos.AdminDto;
import com.db.dbapp.model.dtos.ProductoDto;
import com.db.dbapp.model.dtos.VendedorDto;
import com.db.dbapp.services.AdminService;

@RestController
@RequestMapping("administracion")
@SuppressWarnings("rawtypes")
public class AdminController {

	private AdminService adminService;
	
    @Autowired
    public AdminController(AdminService adminService) {
	this.adminService = adminService;
    }

    @RequestMapping(path = "/admin/crear/", method = RequestMethod.POST, produces = "Application/json")
    public ResponseEntity<AdminDto> crearAdmin(@RequestBody AdminDto adminDto) {
		Admin admin = AdminMapper.toAdmin(new Admin(), adminDto);
		admin = adminService.crear(admin);
		adminDto = AdminMapper.toAdminDto(adminDto, admin);
		return new ResponseEntity<AdminDto>(adminDto, HttpStatus.CREATED);
		}


    @RequestMapping(path = "/admin/actualizar/{id}", method = RequestMethod.PUT, produces = "Application/json")
    public ResponseEntity<AdminDto> actualizarAdmin(@PathVariable(name = "id", required = true) Long id,
	    @RequestBody AdminDto adminDto) {
	try {
	   	Admin admin = adminService.obtenerPorId(id);
	    admin = AdminMapper.toAdmin(admin, adminDto);
	    adminService.actualizar(admin);
	    adminDto = AdminMapper.toAdminDto(adminDto, admin);
	    return new ResponseEntity<AdminDto>(adminDto, HttpStatus.OK);

	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.OK);
	}
    }

    @RequestMapping(path = "/admin/borrar/{id}", method = RequestMethod.DELETE, produces = "Application/json")
    public ResponseEntity borrarAdmin(@PathVariable(name = "id", required = true) Long id) {
	try {
	    adminService.eliminar(id);
	    return new ResponseEntity("Admin " + id + " eliminado", HttpStatus.OK);
	} catch (Throwable e) {
	    e.printStackTrace();
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}
    }

    @RequestMapping(path = "/admin/listar", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<AdminDto>> listarAdmin() {
	List<Admin> admin = new ArrayList<>();
	List<AdminDto> adminDto = new ArrayList<>();
	admin = adminService.listar();
	adminDto = admin.stream().map(v -> AdminMapper.toAdminDto(new AdminDto(), v))
		.collect(Collectors.toList());
	return new ResponseEntity<List<AdminDto>>(adminDto, HttpStatus.OK);
    }

}
