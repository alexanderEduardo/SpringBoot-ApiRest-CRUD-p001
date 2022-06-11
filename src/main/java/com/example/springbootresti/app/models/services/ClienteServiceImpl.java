package com.example.springbootresti.app.models.services;

import com.example.springbootresti.app.models.dao.IClienteDao;
import com.example.springbootresti.app.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findById(long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Transactional
    @Override
    public void delete(long id) {
        clienteDao.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        clienteDao.deleteAll();
    }


}
