package proyecto.msincidencias.aplication.ports.out;

import proyecto.msincidencias.domion.model.Incidencias;

public interface IncidenciaEventPublisherPort {

    void publicarIncidenteCreado(Incidencias incidencias);
    void publirIncidenteAsignado(Incidencias incidencias);
    void publicarIncidenteActualizado(Incidencias incidencias);
    void publicarIncidenteCerrado(Incidencias incidencias);
}
