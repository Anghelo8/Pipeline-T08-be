package pe.edu.vallegrande.losreyes.rest;

import lombok.RequiredArgsConstructor;
import pe.edu.vallegrande.losreyes.model.Sale;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.losreyes.service.SaleService;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleRest {

    private final SaleService service;

    @GetMapping
    public Flux<Sale> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Sale> getById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    public Mono<Sale> create(@RequestBody Sale sale) {
        return service.save(sale);
    }

    @PutMapping("/{id}")
    public Mono<Sale> update(@PathVariable String id, @RequestBody Sale sale) {
        return service.update(id, sale);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }
}