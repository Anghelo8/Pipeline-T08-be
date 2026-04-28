package pe.edu.vallegrande.losreyes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.losreyes.model.Purchase;
import pe.edu.vallegrande.losreyes.model.PurchaseDetail;
import pe.edu.vallegrande.losreyes.repository.PurchaseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository repository;

    // 🔥 ZONA HORARIA PERÚ
    private static final ZoneId ZONA_PERU = ZoneId.of("America/Lima");

    // ✅ LISTAR TODO
    public Flux<Purchase> findAll() {
        return repository.findAll();
    }

    // ✅ BUSCAR POR ID
    public Mono<Purchase> findById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Compra no encontrada")));
    }

    // 🔥 CREAR (POST)
    public Mono<Purchase> create(Purchase purchase) {

        purchase.setId(null);

        if (purchase.getNombreCliente() == null || purchase.getNombreCliente().isBlank()) {
            return Mono.error(new RuntimeException("Nombre es obligatorio"));
        }

        if (purchase.getDni() == null || !purchase.getDni().matches("\\d{8}")) {
            return Mono.error(new RuntimeException("DNI inválido (8 dígitos)"));
        }

        if (purchase.getMetodoPago() == null || purchase.getMetodoPago().isBlank()) {
            return Mono.error(new RuntimeException("Método de pago obligatorio"));
        }

        if (purchase.getDetalles() == null || purchase.getDetalles().isEmpty()) {
            return Mono.error(new RuntimeException("Debe agregar productos"));
        }

        BigDecimal total = BigDecimal.ZERO;

        for (PurchaseDetail d : purchase.getDetalles()) {

            if (d.getCantidad() == null || d.getCantidad() <= 0) {
                return Mono.error(new RuntimeException("Cantidad inválida"));
            }

            if (d.getPrecio() == null || d.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                return Mono.error(new RuntimeException("Precio inválido"));
            }

            BigDecimal subtotal = d.getPrecio()
                    .multiply(BigDecimal.valueOf(d.getCantidad()));

            total = total.add(subtotal);
        }

        purchase.setTotal(total);

        // 🔥 AUDITORÍA (HORA PERÚ)
        purchase.setStatus(true);
        purchase.setCreatedAt(LocalDateTime.now(ZONA_PERU));
        purchase.setUpdatedAt(null);
        purchase.setDeletedAt(null);
        purchase.setRestoredAt(null);

        return repository.save(purchase);
    }

    // 🔥 EDITAR (PUT)
    public Mono<Purchase> update(String id, Purchase purchase) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Compra no encontrada")))
                .flatMap(existing -> {

                    if (purchase.getNombreCliente() == null || purchase.getNombreCliente().isBlank()) {
                        return Mono.error(new RuntimeException("Nombre es obligatorio"));
                    }

                    if (purchase.getDni() == null || !purchase.getDni().matches("\\d{8}")) {
                        return Mono.error(new RuntimeException("DNI inválido"));
                    }

                    if (purchase.getMetodoPago() == null || purchase.getMetodoPago().isBlank()) {
                        return Mono.error(new RuntimeException("Método de pago obligatorio"));
                    }

                    if (purchase.getDetalles() == null || purchase.getDetalles().isEmpty()) {
                        return Mono.error(new RuntimeException("Debe agregar productos"));
                    }

                    BigDecimal total = BigDecimal.ZERO;

                    for (PurchaseDetail d : purchase.getDetalles()) {

                        if (d.getCantidad() == null || d.getCantidad() <= 0) {
                            return Mono.error(new RuntimeException("Cantidad inválida"));
                        }

                        if (d.getPrecio() == null || d.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                            return Mono.error(new RuntimeException("Precio inválido"));
                        }

                        BigDecimal subtotal = d.getPrecio()
                                .multiply(BigDecimal.valueOf(d.getCantidad()));

                        total = total.add(subtotal);
                    }

                    existing.setNombreCliente(purchase.getNombreCliente());
                    existing.setDni(purchase.getDni());
                    existing.setMetodoPago(purchase.getMetodoPago());
                    existing.setTipoComprobante(purchase.getTipoComprobante());
                    existing.setDetalles(purchase.getDetalles());
                    existing.setTotal(total);

                    // 🔥 HORA PERÚ
                    existing.setUpdatedAt(LocalDateTime.now(ZONA_PERU));

                    return repository.save(existing);
                });
    }

    // 🔥 ELIMINAR LÓGICO (PATCH)
    public Mono<Purchase> deleteLogical(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Compra no encontrada")))
                .flatMap(p -> {
                    p.setStatus(false);
                    p.setDeletedAt(LocalDateTime.now(ZONA_PERU));
                    return repository.save(p);
                });
    }

    // 🔥 RESTAURAR (PATCH)
    public Mono<Purchase> restore(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Compra no encontrada")))
                .flatMap(p -> {
                    p.setStatus(true);
                    p.setRestoredAt(LocalDateTime.now(ZONA_PERU));
                    return repository.save(p);
                });
    }
}