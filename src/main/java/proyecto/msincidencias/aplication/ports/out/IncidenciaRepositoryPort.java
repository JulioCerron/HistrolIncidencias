package proyecto.msincidencias.aplication.ports.out;

import proyecto.msincidencias.domion.model.Incidencias;

import java.util.Optional;

public interface IncidenciaRepositoryPort {
    Incidencias guardar (Incidencias incidencias);
    Optional<Incidencias> buscarPorId(Long id);
}
