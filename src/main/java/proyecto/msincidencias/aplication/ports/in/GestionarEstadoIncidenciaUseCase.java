package proyecto.msincidencias.aplication.ports.in;

import proyecto.msincidencias.domion.model.Estado;
import proyecto.msincidencias.domion.model.Incidencias;

public interface GestionarEstadoIncidenciaUseCase {

    Incidencias cambiarEstado(Long idIncidencia , Estado nuevEstado);
    Incidencias asignarResponsable(Long idIncidencia, Long idResponsable);
}
