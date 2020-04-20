package com.db.dbapp.mappers;

import com.db.dbapp.model.Carro;
import com.db.dbapp.model.Persona;
import com.db.dbapp.model.dtos.CarroDto;
import com.db.dbapp.model.dtos.ClienteDto;
import com.db.dbapp.model.dtos.PersonaDto;
import com.db.dbapp.model.dtos.VendedorDto;

public class CarroMapper {

    public static Carro toCompra(Carro carro, CarroDto carroDto) {
	carro.setFecha(carroDto.getFecha());
	return carro;
    }

    public static CarroDto toCarroDto(CarroDto carroDto, Carro carro) {
	carroDto.setFecha(carro.getFecha());
	carroDto.setId(carro.getId());
	carroDto.setCliente(ClienteMapper.toClienteDto(new ClienteDto(), carro.getCliente(), null));
//	carroDto.setVendedor(VendedorMapper.toVendedorDto(new VendedorDto(), carro.getVendedor()));
//	carroDto.setUsuario(UsuarioMapper.toUsuarioDto(new PersonaDto(), (Persona) carro.getUsuario()));
	carroDto.setCliente(ClienteMapper.toClienteDto(new ClienteDto(), carro.getCliente(), null));
	carroDto.setStatus(carro.getStatus().getStatus());
	return carroDto;
    }

}
