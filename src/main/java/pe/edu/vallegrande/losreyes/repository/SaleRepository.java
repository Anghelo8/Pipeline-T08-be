package pe.edu.vallegrande.losreyes.repository;

import pe.edu.vallegrande.losreyes.model.Sale;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SaleRepository extends ReactiveMongoRepository<Sale, String> {

    Mono<Sale> findBySaleId(int saleId);

}