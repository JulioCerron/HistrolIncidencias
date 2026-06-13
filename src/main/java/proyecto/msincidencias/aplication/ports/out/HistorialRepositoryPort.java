package proyecto.msincidencias.aplication.ports.out;

import proyecto.msincidencias.domion.model.HistorialIncidencia;

import java.util.List;

public interface HistorialRepositoryPort {
    void guardar(HistorialIncidencia historial);
    List<HistorialIncidencia> buscarPorIncidenciaId(Long incidenciaId);
}
