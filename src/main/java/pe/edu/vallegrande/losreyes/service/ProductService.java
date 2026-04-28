package pe.edu.vallegrande.losreyes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.losreyes.model.Product;
import pe.edu.vallegrande.losreyes.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Flux<Product> findAll() {
        return repository.findAll();
    }

    public Flux<Product> findAllActive() {
        return repository.findByStatus(true);
    }

    public Flux<Product> findAllInactive() {
        return repository.findByStatus(false);
    }

    public Mono<Product> findById(String id) {
        return repository.findById(id);
    }

    public Mono<Product> create(Product product) {
        product.setStatus(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(null);
        product.setDeletedAt(null);
        product.setRestoredAt(null);
        return repository.save(product);
    }

    public Mono<Product> update(String id, Product product) {
        return repository.findById(id)
                .flatMap(existing -> {
                    product.setId(id);
                    product.setCreatedAt(existing.getCreatedAt());
                    product.setDeletedAt(existing.getDeletedAt());
                    product.setRestoredAt(existing.getRestoredAt());
                    product.setUpdatedAt(LocalDateTime.now());

                    if (product.getStatus() == null) {
                        product.setStatus(existing.getStatus());
                    }

                    return repository.save(product);
                });
    }

    public Mono<Product> deleteLogical(String id) {
        return repository.findById(id)
                .flatMap(product -> {
                    product.setStatus(false);
                    product.setDeletedAt(LocalDateTime.now());
                    return repository.save(product);
                });
    }

    public Mono<Product> restore(String id) {
        return repository.findById(id)
                .flatMap(product -> {
                    product.setStatus(true);
                    product.setRestoredAt(LocalDateTime.now());
                    return repository.save(product);
                });
    }
}