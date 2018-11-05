package com.zoo.web.DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zoo.web.entity.Usuario;

@Transactional
@Repository
public class UsuarioDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IUsuarioDAO crud;
	
	public IUsuarioDAO crud() {
		return this.crud;
	}
	
	public Usuario buscarPorNombreUsuario(String nombreUsuario) {
		
		String hql = "select u from Usuario u where u.nombreUsuario = :user";
		
		return (Usuario)em.createQuery(hql, Usuario.class)
				.setParameter("user", nombreUsuario)
				.getSingleResult();
	}
	
	
	
}