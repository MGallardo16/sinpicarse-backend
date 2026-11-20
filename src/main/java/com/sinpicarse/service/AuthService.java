package com.sinpicarse.service;
import com.sinpicarse.dto.AuthResponse;
import com.sinpicarse.dto.LoginRequest;
import com.sinpicarse.dto.RegistroRequest;
import com.sinpicarse.model.Rol;
import com.sinpicarse.model.Usuario;
import com.sinpicarse.repository.UsuarioRepository;
import com.sinpicarse.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registrar(RegistroRequest request) {
        if (!esDominioValido(request.getCorreo())) {
            throw new IllegalArgumentException("Dominio de correo no permitido. Usa @gmail.com, @duocuc.cl o @profesor.duocuc.cl");
        }
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado");
        }

        Rol rolAsignado = (request.getRol() != null) ? request.getRol() : Rol.CLIENTE;

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(rolAsignado)
                .build();

        Usuario guardado = usuarioRepository.save(nuevoUsuario);
        String jwt = jwtService.generarToken(guardado);

        return AuthResponse.builder()
                .token(jwt)
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .correo(guardado.getCorreo())
                .rol(guardado.getRol())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String jwt = jwtService.generarToken(usuario);

        return AuthResponse.builder()
                .token(jwt)
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol())
                .build();
    }

    private boolean esDominioValido(String correo) {
        return correo != null && (
                correo.endsWith("@gmail.com") ||
                correo.endsWith("@duocuc.cl") ||
                correo.endsWith("@profesor.duocuc.cl")
        );
    }
}