package proyecto.msincidencias.domion.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HistorialIncidencia {
    private Long id;
    private Long incidenciaId;
    private String descripcionCambio;
    private Estado estadoAnterior;
    private Estado estadoNuevo;
    private LocalDateTime fechaCambio;




}
