package proyecto.msincidencias.infraestructura.rest.dto;

import proyecto.msincidencias.domion.model.Prioridad;

public record IncidenciaRequestDTO(
        Prioridad prioridad,
        String descripcion,
        String area
) {
}
