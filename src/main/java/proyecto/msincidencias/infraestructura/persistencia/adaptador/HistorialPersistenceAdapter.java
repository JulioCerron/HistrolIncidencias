package proyecto.msincidencias.infraestructura.persistencia.adaptador;

import lombok.RequiredArgsConstructor;
import proyecto.msincidencias.aplication.ports.out.HistorialRepositoryPort;
import proyecto.msincidencias.domion.model.Estado;
import proyecto.msincidencias.domion.model.HistorialIncidencia;
import proyecto.msincidencias.infraestructura.persistencia.HistorialIncidenciaEntity;
import proyecto.msincidencias.infraestructura.persistencia.repository.SpringDataHistorialRepository;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HistorialPersistenceAdapter implements HistorialRepositoryPort {

    private final SpringDataHistorialRepository repository;


    @Override
    public void guardar(HistorialIncidencia historial) {
        HistorialIncidenciaEntity entity = new HistorialIncidenciaEntity();
        entity.setIncidenciaId(historial.getIncidenciaId());
        entity.setDescripcionCambio(historial.getDescripcionCambio());
        entity.setEstadoAnterior(historial.getEstadoAnterior() != null ? historial.getEstadoAnterior().name(): null);
        entity.setEstadoNuevo(historial.getEstadoNuevo().name());
        entity.setFechaCambio(historial.getFechaCambio());
        repository.save(entity);
    }

    @Override
    public List<HistorialIncidencia> buscarPorIncidenciaId(Long incidenciaId) {
        return repository.findByIncidenciaIdOrderByFechaCambioDesc(incidenciaId).stream().map(entity -> {
            HistorialIncidencia domain = new HistorialIncidencia();
            domain.setId(entity.getId());
            domain.setIncidenciaId(entity.getIncidenciaId());
            domain.setDescripcionCambio(entity.getDescripcionCambio());
            domain.setEstadoAnterior(entity.getEstadoAnterior() != null ? Estado.valueOf(entity.getEstadoAnterior()) : null);
            domain.setEstadoNuevo(Estado.valueOf(entity.getEstadoNuevo()));
            domain.setFechaCambio(entity.getFechaCambio());
            return domain;
        }).collect(Collectors.toList());
    }
}
