package pe.edu.vallegrande.losreyes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.losreyes.model.Customer;
import pe.edu.vallegrande.losreyes.repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Flux<Customer> findAll() {
        return repository.findAll();
    }

    public Flux<Customer> findAllActive() {
        return repository.findByStatus(true);
    }

    public Flux<Customer> findAllInactive() {
        return repository.findByStatus(false);
    }

    public Mono<Customer> findById(String id) {
        return repository.findById(id);
    }

    // ✨ CORREGIDO: Al crear, updatedAt queda en null (o no se setea)
    public Mono<Customer> create(Customer customer) {
        customer.setStatus(true);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(null); // O simplemente no llamarlo si por defecto es null
        customer.setDeletedAt(null);
        customer.setRestoredAt(null);
        return repository.save(customer);
    }

    // 📝 Único lugar donde se genera la fecha de actualización
    public Mono<Customer> update(String id, Customer customer) {
        return repository.findById(id)
                .flatMap(existing -> {
                    customer.setId(id);
                    customer.setCreatedAt(existing.getCreatedAt());
                    customer.setDeletedAt(existing.getDeletedAt());
                    customer.setRestoredAt(existing.getRestoredAt());
                    
                    // Solo aquí marcamos que los datos fueron editados
                    customer.setUpdatedAt(LocalDateTime.now());

                    if (customer.getStatus() == null) {
                        customer.setStatus(existing.getStatus());
                    }
                    return repository.save(customer);
                });
    }

    public Mono<Customer> deleteLogical(String id) {
        return repository.findById(id)
                .flatMap(customer -> {
                    customer.setStatus(false);
                    customer.setDeletedAt(LocalDateTime.now());
                    // No tocamos updatedAt
                    return repository.save(customer);
                });
    }

    public Mono<Customer> restore(String id) {
        return repository.findById(id)
                .flatMap(customer -> {
                    customer.setStatus(true);
                    customer.setRestoredAt(LocalDateTime.now());
                    // No tocamos updatedAt
                    return repository.save(customer);
                });
    }

    public Mono<Void> deletePhysical(String id) {
        return repository.deleteById(id);
    }
}