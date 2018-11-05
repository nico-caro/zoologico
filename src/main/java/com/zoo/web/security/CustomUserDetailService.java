package com.zoo.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.zoo.web.DAO.UsuarioDAO;
import com.zoo.web.entity.Usuario;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDAO.buscarPorNombreUsuario(username);
		
		//crearemos un Userbuilder para crear un usuario de
		//Spring
		
		UserBuilder user = null;
		
		if(usuario!=null) {
			//si existe el usuario lo transformo en un usuario
			//de Spring
			
			user = org.springframework.security.core.userdetails.User.withUsername(username);
			user.password(new BCryptPasswordEncoder().encode(usuario.getPassword()));
			//le pasamos el rol que tiene el usuario desde la BBDD
			//error en roles por la BD
			//user.roles(String.valueOf(usuario.getRol().getNombre()));
			
			
		} else {
			//si el usuario no existe mandaremos una excepcion
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		return user.build();
	}

	
	
}
