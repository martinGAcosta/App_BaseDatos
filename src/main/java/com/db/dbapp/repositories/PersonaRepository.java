package com.db.dbapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.dbapp.model.Persona;

@Repository
public interface PersonaRepository<T extends Persona, Long> extends CrudRepository<T, Long> {

    Optional<T> findByEmail(String email);

    Optional<T> findByApellidoAndNombre(String apellido, String nombre);

}
