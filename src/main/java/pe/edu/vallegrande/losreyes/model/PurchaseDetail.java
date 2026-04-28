package pe.edu.vallegrande.losreyes.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetail {

    private String productId;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precio;
}