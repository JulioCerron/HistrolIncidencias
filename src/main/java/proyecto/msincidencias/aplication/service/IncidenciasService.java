package proyecto.msincidencias.aplication.service;

import lombok.RequiredArgsConstructor;
import proyecto.msincidencias.aplication.ports.in.ConsultarHistorialUseCase;
import proyecto.msincidencias.aplication.ports.in.GestionarEstadoIncidenciaUseCase;
import proyecto.msincidencias.aplication.ports.in.RegistrarIncidenciaUseCase;
import proyecto.msincidencias.aplication.ports.out.HistorialRepositoryPort;
import proyecto.msincidencias.aplication.ports.out.IncidenciaEventPublisherPort;
import proyecto.msincidencias.aplication.ports.out.IncidenciaRepositoryPort;
import proyecto.msincidencias.domion.model.Estado;
import proyecto.msincidencias.domion.model.HistorialIncidencia;
import proyecto.msincidencias.domion.model.Incidencias;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class IncidenciasService implements RegistrarIncidenciaUseCase, GestionarEstadoIncidenciaUseCase , ConsultarHistorialUseCase {

    private final IncidenciaRepositoryPort repositoryPort;
    private final IncidenciaEventPublisherPort eventPublisherPort;
    private final HistorialRepositoryPort historialRepositoryPort;

    @Override
    public Incidencias cambiarEstado(Long idIncidencia, Estado nuevEstado) {
        Incidencias incidencias= repositoryPort.buscarPorId(idIncidencia)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        if(incidencias.getEstado() == Estado.CERRADA){
            throw new IllegalStateException("Una incidencia cerrada no podra modificarse.");
        }

        // 1. CAPTURAMOS EL ESTADO ANTERIOR ANTES DE CAMBIARLO
        Estado estadoAnterior = incidencias.getEstado();

        incidencias.setEstado(nuevEstado);
        Incidencias actualizada = repositoryPort.guardar(incidencias);

        // 2. GUARDAMOS EL HISTORIAL
        HistorialIncidencia historial = HistorialIncidencia.builder()
                .incidenciaId(actualizada.getId())
                .estadoAnterior(estadoAnterior)
                .estadoNuevo(Estado.ASIGNADA)
                .fechaCambio(LocalDateTime.now())
                .build();

        historialRepositoryPort.guardar(historial);

        //Publicar evento segun el estado
        if (nuevEstado == Estado.CERRADA){
            eventPublisherPort.publicarIncidenteCerrado(actualizada);
        } else {
            eventPublisherPort.publicarIncidenteActualizado(actualizada);
        }
        return actualizada;
    }

    @Override
    public Incidencias asignarResponsable(Long idIncidencia, Long idResponsable) {
        Incidencias incidencias = repositoryPort.buscarPorId(idIncidencia)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        if (incidencias.getEstado() == Estado.CERRADA) {
            throw new IllegalStateException("No se puede asignar un responsable a una incidencia cerrada.");
        }

        // 1. CAPTURAMOS EL ESTADO ANTERIOR
        Estado estadoAnterior = incidencias.getEstado();

        incidencias.setResponsableId(idResponsable);
        incidencias.setEstado(Estado.ASIGNADA);

        Incidencias actualizada = repositoryPort.guardar(incidencias);

        // 2. GUARDAMOS EL HISTORIAL
        HistorialIncidencia historial = HistorialIncidencia.builder()
                .incidenciaId(actualizada.getId())
                .descripcionCambio("Se asignó el responsable con ID: " + idResponsable)
                .estadoAnterior(estadoAnterior)
                .estadoNuevo(Estado.ASIGNADA)
                .fechaCambio(LocalDateTime.now())
                .build();

        historialRepositoryPort.guardar(historial);

        eventPublisherPort.publirIncidenteAsignado(actualizada);

        return actualizada;
    }

    @Override
    public Incidencias registrar(Incidencias incidencias) {
        //Logica de negocio :Validaciones
        if(incidencias.getPrioridad()==null ||
                incidencias.getDescripcion()== null || incidencias.getDescripcion().trim().isEmpty() ||
                incidencias.getArea()== null || incidencias.getArea().trim().isEmpty()){
            throw new IllegalArgumentException("La incidencia debe tener: Prioridad , Descripcion , y Area.");
        }

        incidencias.setEstado(Estado.REGISTRADA);
        incidencias.setFechaCreacion(java.time.LocalDateTime.now());

        Incidencias guaradada = repositoryPort.guardar(incidencias);

        // GUARDAMOS EL HISTORIAL DE CREACIÓN (Estado anterior es null porque recién nace)
        HistorialIncidencia historial = HistorialIncidencia.builder()
                .estadoNuevo(Estado.ASIGNADA)
                .fechaCambio(LocalDateTime.now())
                .build();

        historialRepositoryPort.guardar(historial);

        eventPublisherPort.publicarIncidenteCreado(guaradada);
        return guaradada;
    }

    @Override
    public List<HistorialIncidencia> consultarPorIncidencia(Long incidenciaId) {
        return historialRepositoryPort.buscarPorIncidenciaId(incidenciaId);
    }
}