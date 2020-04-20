package com.db.dbapp.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.dbapp.model.Persona;
import com.db.dbapp.model.Producto;

@Repository
public interface PersonaRepository<T extends Persona, Long> extends CrudRepository<T, Long> {

	@Query(value = "SELECT * FROM Persona WHERE id = ?1 and DTYPE = ?2", nativeQuery = true)
	List<Persona> findByIdandType(Long id, String tipo);
	
}
