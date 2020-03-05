package com.db.dbapp.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.dbapp.model.Admin;

@Repository
@Transactional
public interface AdminRepository extends PersonaRepository<Admin, Long> {

}
