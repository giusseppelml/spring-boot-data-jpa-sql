package com.curso.java.spring.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.curso.java.spring.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{

}
