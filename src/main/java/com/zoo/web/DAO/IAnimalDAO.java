package com.zoo.web.DAO;

import org.springframework.data.repository.CrudRepository;

import com.zoo.web.entity.Animal;

public interface IAnimalDAO extends CrudRepository<Animal, Integer>{

}
