package com.db.dbapp.services;

import java.util.List;

public interface IService<T> {

    T obtenerPorId(Long id) throws Throwable;

    T crear(T entity);

    void actualizar(T entity);

    void eliminar(Long id);

    List<T> listar();

}
