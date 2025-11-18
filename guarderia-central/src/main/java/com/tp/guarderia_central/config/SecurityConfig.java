package com.tp.guarderia_central.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()
                .requestMatchers(HttpMethod.POST, "/socios/guardar", "/socios/actualizar/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/socios/agregar", "/socios/editar/**", "/socios/eliminar/**").hasRole("ADMIN")
                .requestMatchers("/empleados/**").hasRole("ADMIN")
                .requestMatchers("/zonas/**").hasRole("ADMIN")
                .requestMatchers("/garages/**").hasRole("ADMIN")
                .requestMatchers("/vehiculos/**").hasRole("ADMIN")
                .requestMatchers("/portal/**").hasAnyRole("SOCIO", "EMPLEADO","ADMIN")
                // TODO: Refinar los permisos de listado. Actualmente permitimos listar a ADMIN, EMPLEADO y SOCIO pero en socio tendrian que ser sus vehiculos y en empleado los que estan en los garages a cargo.
                .requestMatchers(HttpMethod.GET, "/vehiculos/listar").hasAnyRole("ADMIN", "EMPLEADO", "SOCIO")
                .requestMatchers(HttpMethod.GET, "/socios/listar").hasAnyRole("ADMIN", "SOCIO", "EMPLEADO")
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }
}