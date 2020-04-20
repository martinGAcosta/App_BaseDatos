package com.db.dbapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.dbapp.model.Producto;

@Repository
@Transactional
public interface ProductoRepository extends CrudRepository<Producto, Long>, JpaRepository<Producto, Long>  {

	@Query(value = "SELECT * FROM Producto WHERE descripcion = ?1", nativeQuery = true)
	List<Producto> findByDescripcion(String descripcion);
	
}
