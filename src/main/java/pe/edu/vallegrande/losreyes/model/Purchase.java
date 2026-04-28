package pe.edu.vallegrande.losreyes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "purchases")
public class Purchase {

    @Id
    private String id;

    // 👤 CLIENTE
    private String nombreCliente;
    private String dni;

    // 💳 PAGO
    private String metodoPago;
    private String tipoComprobante;

    // 💰 TOTAL (AUTOCALCULADO)
    private BigDecimal total;

    // 📦 DETALLE
    private List<PurchaseDetail> detalles;

    private Boolean status = true;

    // 🔥 AUDITORÍA
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime restoredAt;
}