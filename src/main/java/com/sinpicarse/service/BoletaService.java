package com.sinpicarse.service;

import com.sinpicarse.dto.BoletaRequest;
import com.sinpicarse.model.Boleta;
import com.sinpicarse.model.DetalleBoleta;
import com.sinpicarse.model.Producto;
import com.sinpicarse.model.Usuario;
import com.sinpicarse.repository.BoletaRepository;
import com.sinpicarse.repository.ProductoRepository;
import com.sinpicarse.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoletaService {

    private final BoletaRepository boletaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Boleta> listarTodas() {
        return boletaRepository.findAllByOrderByFechaDesc();
    }

    public Boleta obtenerPorId(Long id) {
        return boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada con ID: " + id));
    }

    @Transactional
    public Boleta generarBoleta(String correoUsuario, BoletaRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Boleta boleta = Boleta.builder()
                .fecha(LocalDateTime.now())
                .direccionEnvio(request.getDireccionEnvio())
                .telefono(request.getTelefono())
                .indicaciones(request.getIndicaciones())
                .usuario(usuario)
                .detalles(new ArrayList<>())
                .build();

        int subtotal = 0;

        for (BoletaRequest.ItemBoletaRequest item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado ID: " + item.getProductoId()));

            if (producto.getStock() < item.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + producto.getNombre());
            }

            // Descontar stock
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            DetalleBoleta detalle = DetalleBoleta.builder()
                    .boleta(boleta)
                    .producto(producto)
                    .cantidad(item.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .build();

            boleta.getDetalles().add(detalle);
            subtotal += producto.getPrecio() * item.getCantidad();
        }

        // Aplicar IVA 19% igual que en el frontend
        int totalConIva = (int) Math.round(subtotal * 1.19);
        boleta.setTotal(totalConIva);

        return boletaRepository.save(boleta);
    }
}
