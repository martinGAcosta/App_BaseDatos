package com.db.dbapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.dbapp.model.Carro;

@Repository
@Transactional
public interface CarroRepository extends CrudRepository<Carro, Long> {

    Optional<Carro> findByIdAndCliente_Id(Long idCompra, Long idCliente);

}
