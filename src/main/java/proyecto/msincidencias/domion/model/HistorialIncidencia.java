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


    public HistorialIncidencia(Long incidenciaId, String descripcionCambio, Estado estadoAnterior, Estado estadoNuevo) {
        this.incidenciaId = incidenciaId;
        this.descripcionCambio = descripcionCambio;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fechaCambio = LocalDateTime.now(); // Se asigna automáticamente
    }

}
