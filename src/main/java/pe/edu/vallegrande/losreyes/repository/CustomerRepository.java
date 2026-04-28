package pe.edu.vallegrande.losreyes.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.losreyes.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    
    Flux<Customer> findByStatus(Boolean status);
    
    Mono<Boolean> existsByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);
}