package com.db.dbapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dbapp.repositories.PersonaRepository;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class PersonaService<T, ID> implements IService<T> {

    @Autowired
    PersonaRepository personaRepository;

    public T obtenerPorId(Long id) throws Throwable {
	return (T) personaRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("No existe usuario con ID " + id));
    }

}
