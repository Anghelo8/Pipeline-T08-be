package pe.edu.vallegrande.losreyes.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.losreyes.model.Purchase;

@Repository
public interface PurchaseRepository extends ReactiveMongoRepository<Purchase, String> {
}