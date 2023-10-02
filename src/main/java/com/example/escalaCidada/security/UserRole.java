package com.example.escalaCidada.security;


public enum UserRole {

		
	Administrador("Administrador"),
    Usuario("Usuario");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
	 
	 
	 
}
