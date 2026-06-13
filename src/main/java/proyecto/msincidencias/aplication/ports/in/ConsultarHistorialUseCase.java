package proyecto.msincidencias.aplication.ports.in;

import proyecto.msincidencias.domion.model.HistorialIncidencia;

import java.util.List;

public interface ConsultarHistorialUseCase {
    List<HistorialIncidencia> consultarPorIncidencia(Long incidenciaId);
}
