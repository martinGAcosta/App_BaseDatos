package com.db.dbapp.repositories;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.dbapp.model.Cliente;

@Repository
@Transactional
public interface ClienteRepository extends PersonaRepository<Cliente, Long> {

    
}
