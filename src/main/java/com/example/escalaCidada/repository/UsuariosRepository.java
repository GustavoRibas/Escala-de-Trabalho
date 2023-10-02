package com.example.escalaCidada.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.escalaCidada.entity.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer>{
	
	@Query("SELECT u FROM Usuario u WHERE username = :username")
	Usuario findByLogin(@Param("username") String username);
	
	@Query("SELECT u From Usuario u")
	List<Usuario> getAllUsuarios();
	
	@Query("SELECT COUNT(idUsuario) FROM Usuario u")
	Integer contagemUsuario();

}
