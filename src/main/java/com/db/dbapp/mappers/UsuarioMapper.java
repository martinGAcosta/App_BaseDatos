package com.db.dbapp.mappers;

import com.db.dbapp.model.Persona;
import com.db.dbapp.model.dtos.PersonaDto;

public class UsuarioMapper {

    public static Persona toUsuario(Persona persona, PersonaDto personaDto) {
	persona.setNombre(personaDto.getNombre());
	persona.setApellido(personaDto.getApellido());
	persona.setEmail(personaDto.getEmail());
	persona.setTelefono(personaDto.getTelefono());
	return persona;
    }

    public static PersonaDto toUsuarioDto(PersonaDto personaDto, Persona persona) {
	if (persona != null) {
	    personaDto.setApellido(persona.getApellido());
	    personaDto.setNombre(persona.getNombre());
	    personaDto.setEmail(persona.getEmail());
	    personaDto.setTelefono(persona.getTelefono());
	    personaDto.setId(persona.getId());
	}
	return personaDto;
    }
}
