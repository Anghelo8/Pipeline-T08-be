package pe.edu.vallegrande.losreyes.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.losreyes.model.Purchase;
import pe.edu.vallegrande.losreyes.service.PurchaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
@Tag(name = "Purchase", description = "Purchase management")
public class PurchaseRest {

    private final PurchaseService service;

    @Operation(summary = "List purchases")
    @GetMapping
    public Flux<Purchase> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Find purchase by ID")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Purchase>> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create purchase")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Purchase> create(@RequestBody Purchase purchase) {
        return service.create(purchase);
    }

    @Operation(summary = "Delete purchase")
    @PatchMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.deleteLogical(id)
                .map(p -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Restore purchase")
    @PatchMapping("/restore/{id}")
    public Mono<ResponseEntity<Void>> restore(@PathVariable String id) {
        return service.restore(id)
                .map(p -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}