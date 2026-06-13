package proyecto.msincidencias.domion.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Incidencias {
    private Long id;
    private Prioridad prioridad;
    private String descripcion;
    private String area;
    private Estado estado;
    private Long responsableId;
    private LocalDateTime fechaCreacion;

    public Incidencias() {}

    public Incidencias(Prioridad prioridad, String descripcion, String area) {
        this.prioridad = prioridad;
        this.descripcion = descripcion;
        this.area = area;
        this.estado = Estado.REGISTRADA;
        this.fechaCreacion = LocalDateTime.now();
    }
}
