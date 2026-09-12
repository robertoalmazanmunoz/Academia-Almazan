package com.academia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
		.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
		
		.authorizeHttpRequests(auth -> auth
				// RECURSOS PUBLICOS
				.requestMatchers("/", "/login", "/registro", "/registro-admin", "/css/**", "/js/**", "/img/**").permitAll()
				
				// API PUBLICA
				.requestMatchers("/api/**").permitAll()
				
				// ADMINISTRACIÓN (Solamente Administrador)
				.requestMatchers("/admin/**").hasRole("Administrador")

				// CRUD DE CURSOS
				.requestMatchers("/cursos/nuevo", "/cursos/guardar", "/cursos/editar/**", "/cursos/eliminar/**")
				.hasRole("Administrador")

				// CRUD DE ESTUDIANTES
				.requestMatchers("/estudiantes/nuevo", "/estudiantes/guardar", "/estudiantes/editar/**", "/estudiantes/eliminar/**")
				.hasRole("Administrador")

				// CONSULTA DE CURSOS Y ESTUDIANTES
				// ADMIN + USER
				.requestMatchers("/cursos", "/cursos/", "/cursos/ver/**", "/estudiantes", "/estudiantes/", "/estudiantes/ver/**")
				.hasAnyRole("Administrador", "Usuario")

				// CUALQUIER OTRA URL
				// Requiere autenticación
				.anyRequest().authenticated())

				// LOGIN
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/", true)
						.permitAll())

				// LOGOUT
				.logout(logout -> logout.logoutSuccessUrl("/").permitAll());
		
		return http.build();
	}

}