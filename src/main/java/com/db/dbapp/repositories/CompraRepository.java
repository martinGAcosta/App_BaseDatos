package com.db.dbapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.dbapp.model.Compra;

@Repository
@Transactional
public interface CompraRepository extends CrudRepository<Compra, Long> {

}
