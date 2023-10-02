package com.example.escalaCidada.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.escalaCidada.entity.Usuario;
import com.example.escalaCidada.repository.UsuariosRepository;
import com.example.escalaCidada.security.UserRole;

@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuariosRepository.findByLogin(username);
	}
	
	
	public void criarUsuarioAdminCasoNaoExista() {
		if(usuariosRepository.findByLogin("gustavo") == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			Usuario admin = new Usuario();
			admin.setNomeUsuario("gustavo");
			admin.setUsername("gustavo");
			admin.setPassword(encoder.encode("1234"));
			admin.setCargo(UserRole.Administrador);
			addUsuario(admin);
		}
	}
	
	public void criarUsuarioUserCasoNaoExista() {
		if(usuariosRepository.findByLogin("gabriel") == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			Usuario usuario = new Usuario();
			usuario.setNomeUsuario("gabriel");
			usuario.setUsername("gabriel");
			usuario.setPassword(encoder.encode("12345"));
			usuario.setCargo(UserRole.Usuario);
			addUsuario(usuario);
		}
	}
	
	
	public void addUsuario(Usuario usuario) {
		usuariosRepository.save(usuario);
	}
	
	public List<Usuario> getAllUsuarios() {
		return usuariosRepository.getAllUsuarios();
	}
	
	public Integer contagemUsuarios() {
		return usuariosRepository.contagemUsuario();
	}
	
	public void deletarUsuario(Integer idUsuario) {
		usuariosRepository.deleteById(idUsuario);
	}

	
}
