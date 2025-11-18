package com.tp.guarderia_central.config;

import com.tp.guarderia_central.models.security.Rol;
import com.tp.guarderia_central.models.enums.RolNombre;
import com.tp.guarderia_central.models.security.Usuario;
import com.tp.guarderia_central.repositories.RolRepository;
import com.tp.guarderia_central.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CargaDatosIniciales {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void cargarDatos() {
        if (rolRepository.count() == 0) {
            log.info("No se encontraron roles. Insertando roles...");
            rolRepository.save(new Rol(RolNombre.ROLE_ADMIN));
            rolRepository.save(new Rol(RolNombre.ROLE_SOCIO));
            rolRepository.save(new Rol(RolNombre.ROLE_EMPLEADO));
        }

        if (!usuarioRepository.findByUsername("admin").isPresent()) {
            log.info("No se encontró el usuario admin. Creando admin...");

            Rol rolAdmin = rolRepository.findByNombre(RolNombre.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Rol ADMIN no encontrado"));

            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); 
            admin.setRoles(List.of(rolAdmin));
            usuarioRepository.save(admin);
            log.info("Usuario 'admin' con contraseña 'admin123' creado.");
        }
    }
}
