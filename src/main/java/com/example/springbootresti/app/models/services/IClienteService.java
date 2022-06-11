package com.example.springbootresti.app.models.services;

import com.example.springbootresti.app.models.entity.Cliente;

import java.util.List;

public interface IClienteService {

    List<Cliente> findAll();

    Cliente findById(long id);

    Cliente save(Cliente cliente);

    void delete(long id);

    void deleteAll();
}
