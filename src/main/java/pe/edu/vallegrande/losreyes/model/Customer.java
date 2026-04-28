package pe.edu.vallegrande.losreyes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class Customer {

    @Id
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    private String firstName;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String ubigeoCode;
    private Byte customerTypeId;
    private Boolean status = true;
    private LocalDate registrationDate;
    private LocalDate lastPurchaseDate;
    private BigDecimal lifetimeValue;
    private String customerPreferences;
    private String location;

    // AUDITORÍA
    @CreatedDate
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt; // Sin anotación automática

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime deletedAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime restoredAt;
}