package com.example.springbootresti.app.models.dao;

import com.example.springbootresti.app.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente,Long> {

}
