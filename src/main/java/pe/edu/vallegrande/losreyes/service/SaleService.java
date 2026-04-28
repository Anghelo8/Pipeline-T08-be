package pe.edu.vallegrande.losreyes.service;

import lombok.RequiredArgsConstructor;
import pe.edu.vallegrande.losreyes.model.Sale;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.losreyes.repository.SaleRepository;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository repository;

    public Flux<Sale> findAll() {
        return repository.findAll();
    }

    public Mono<Sale> findById(String id) {
        return repository.findById(id);
    }

    public Mono<Sale> save(Sale sale) {
        return repository.save(sale);
    }

    public Mono<Sale> update(String id, Sale sale) {
        return repository.findById(id)
                .flatMap(existing -> {
                    sale.setId(existing.getId());
                    return repository.save(sale);
                });
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}