package pe.edu.vallegrande.losreyes.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.losreyes.model.Product;
import pe.edu.vallegrande.losreyes.service.ProductService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product management")
public class ProductRest {

    private final ProductService service;

    @Operation(summary = "List active products")
    @GetMapping
    public Flux<Product> getAllActive() {
        return service.findAllActive();
    }

    @Operation(summary = "List all products")
    @GetMapping("/all")
    public Flux<Product> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Find product by ID")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> create(@RequestBody Product product) {
        return service.create(product);
    }

    @Operation(summary = "Update product")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> update(@PathVariable String id,
                                                @RequestBody Product product) {
        return service.update(id, product)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete product")
    @PatchMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.deleteLogical(id)
                .map(p -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Restore product")
    @PatchMapping("/restore/{id}")
    public Mono<ResponseEntity<Void>> restore(@PathVariable String id) {
        return service.restore(id)
                .map(p -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}