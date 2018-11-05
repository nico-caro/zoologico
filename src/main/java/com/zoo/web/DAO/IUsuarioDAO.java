package com.zoo.web.DAO;

import org.springframework.data.repository.CrudRepository;

import com.zoo.web.entity.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Integer> {

	
}