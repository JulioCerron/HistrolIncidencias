package proyecto.msincidencias.infraestructura.persistencia.mensajeria.dto;

import proyecto.msincidencias.domion.model.Incidencias;

public record IncidenciaEventDTO(
        Long id,
        String prioridad,
        String descripcion,
        String area,
        String estado,
        Long responsableId,
        String fechaCreacion
) {
    // Método estático de ayuda (Mapper) para convertir fácilmente desde el Dominio al DTO
    public static IncidenciaEventDTO desdeDominio(Incidencias incidencia) {
        return new IncidenciaEventDTO(
                incidencia.getId(),
                incidencia.getPrioridad() != null ? incidencia.getPrioridad().name() : null,
                incidencia.getDescripcion(),
                incidencia.getArea(),
                incidencia.getEstado() != null ? incidencia.getEstado().name() : null,
                incidencia.getResponsableId(),
                incidencia.getFechaCreacion() != null ? incidencia.getFechaCreacion().toString() : null
        );
    }
}



