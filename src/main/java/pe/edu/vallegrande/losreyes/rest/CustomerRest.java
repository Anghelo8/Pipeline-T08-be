package pe.edu.vallegrande.losreyes.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.losreyes.model.Customer;
import pe.edu.vallegrande.losreyes.service.CustomerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Gestión de Clientes", description = "Endpoints para clientes en MongoDB")
public class CustomerRest {

    private final CustomerService customerService;

    // 🔹 SOLO ACTIVOS
    @Operation(summary = "Listar clientes activos")
    @GetMapping
    public Flux<Customer> getAll() {
        return customerService.findAllActive();
    }

    // 🔹 NUEVO: TODOS (activos + inactivos)
    @Operation(summary = "Listar todos los clientes")
    @GetMapping("/all")
    public Flux<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> getById(@PathVariable String id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Filtrar por estado")
    @GetMapping("/status/{status}")
    public Flux<Customer> getByStatus(@PathVariable String status) {
        if ("INACTIVE".equalsIgnoreCase(status)) {
            return customerService.findAllInactive();
        }
        return customerService.findAllActive();
    }

    @Operation(summary = "Registrar cliente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @Operation(summary = "Actualizar cliente")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Customer>> update(
            @PathVariable String id,
            @RequestBody Customer customer) {
        return customerService.update(id, customer)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminación lógica")
    @PatchMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return customerService.deleteLogical(id)
                .map(c -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Restaurar cliente")
    @PatchMapping("/restore/{id}")
    public Mono<ResponseEntity<Void>> restore(@PathVariable String id) {
        return customerService.restore(id)
                .map(c -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}