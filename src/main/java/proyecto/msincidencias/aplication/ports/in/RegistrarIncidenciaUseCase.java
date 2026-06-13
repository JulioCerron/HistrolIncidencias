package proyecto.msincidencias.aplication.ports.in;

import proyecto.msincidencias.domion.model.Incidencias;

public interface RegistrarIncidenciaUseCase {

    Incidencias registrar(Incidencias incidencias);
}
