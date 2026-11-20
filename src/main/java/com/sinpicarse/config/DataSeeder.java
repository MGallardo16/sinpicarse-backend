package com.sinpicarse.config;

import com.sinpicarse.model.Categoria;
import com.sinpicarse.model.Producto;
import com.sinpicarse.model.Rol;
import com.sinpicarse.model.Usuario;
import com.sinpicarse.repository.CategoriaRepository;
import com.sinpicarse.repository.ProductoRepository;
import com.sinpicarse.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(Usuario.builder()
                    .nombre("Administrador SinPicarse")
                    .correo("admin@duocuc.cl")
                    .password(passwordEncoder.encode("admin123"))
                    .rol(Rol.ADMIN)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Vendedor Tienda")
                    .correo("vendedor@duocuc.cl")
                    .password(passwordEncoder.encode("vendedor123"))
                    .rol(Rol.VENDEDOR)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Cliente Prueba")
                    .correo("cliente@gmail.com")
                    .password(passwordEncoder.encode("cliente123"))
                    .rol(Rol.CLIENTE)
                    .build());
        }

        if (categoriaRepository.count() == 0) {
            Categoria familiares = categoriaRepository.save(Categoria.builder().nombre("Familiares").build());
            Categoria estrategia = categoriaRepository.save(Categoria.builder().nombre("Estrategia").build());
            Categoria party = categoriaRepository.save(Categoria.builder().nombre("Party Games").build());

            productoRepository.save(Producto.builder()
                    .nombre("Catan: El Juego")
                    .descripcion("Construye poblados, caminos y comercia recursos en la isla de Catan.")
                    .precio(34990)
                    .stock(15)
                    .imagen("https://images.unsplash.com/photo-1610890716171-6b1bb98ffd09?w=500")
                    .categoria(estrategia)
                    .build());

            productoRepository.save(Producto.builder()
                    .nombre("Dixit")
                    .descripcion("Un juego de mesa creativo de narración e ilustraciones oníricas.")
                    .precio(27990)
                    .stock(20)
                    .imagen("https://images.unsplash.com/photo-1632501641765-e568d28b0015?w=500")
                    .categoria(familiares)
                    .build());

            productoRepository.save(Producto.builder()
                    .nombre("Exploding Kittens")
                    .descripcion("Un juego de cartas altamente estratégico impulsado por gatitos explosivos.")
                    .precio(18990)
                    .stock(30)
                    .imagen("https://images.unsplash.com/photo-1606167668584-78701c57f13d?w=500")
                    .categoria(party)
                    .build());
        }
    }
}