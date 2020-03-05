package com.db.dbapp.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.dbapp.model.Vendedor;

@Repository
@Transactional
public interface VendedorRepository extends PersonaRepository<Vendedor, Long> {

    Optional<Vendedor> findByCodigo(String cod);

}
