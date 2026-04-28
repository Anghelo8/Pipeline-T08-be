package pe.edu.vallegrande.losreyes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    private String productName;
    private String attributes;
    private Integer categoryId;
    private BigDecimal unitPrice;
    private Integer stock;
    private BigDecimal alcoholPercentage;

    private Boolean status = true;

    // Auditoría
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime restoredAt;
}