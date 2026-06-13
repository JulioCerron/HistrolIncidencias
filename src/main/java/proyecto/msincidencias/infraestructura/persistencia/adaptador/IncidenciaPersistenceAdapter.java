package proyecto.msincidencias.infraestructura.persistencia.adaptador;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import proyecto.msincidencias.aplication.ports.out.IncidenciaEventPublisherPort;
import proyecto.msincidencias.aplication.ports.out.IncidenciaRepositoryPort;
import proyecto.msincidencias.domion.model.Incidencias;
import proyecto.msincidencias.infraestructura.persistencia.IncidenciaEntity;
import proyecto.msincidencias.infraestructura.persistencia.repository.SpringDataIncidenciaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class IncidenciaPersistenceAdapter implements IncidenciaRepositoryPort {

    private final SpringDataIncidenciaRepository repositoryPort;


    @Override
    public Incidencias guardar(Incidencias incidencias) {

        IncidenciaEntity entity = toEntity(incidencias);
        IncidenciaEntity savedEntity = repositoryPort.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Incidencias> buscarPorId(Long id) {
        return repositoryPort.findById(id).map(this::toDomain);
    }

    private IncidenciaEntity toEntity(Incidencias domain) {
        IncidenciaEntity entity = new IncidenciaEntity();
        entity.setId(domain.getId());
        entity.setPrioridad(domain.getPrioridad());
        entity.setDescripcion(domain.getDescripcion());
        entity.setArea(domain.getArea());
        entity.setEstado(domain.getEstado());
        entity.setResponsableId(domain.getResponsableId());
        entity.setFechaCreacion(domain.getFechaCreacion());
        return entity;

    }
    private Incidencias toDomain(IncidenciaEntity entity) {
        Incidencias domain = new Incidencias();
        domain.setId(entity.getId());
        domain.setPrioridad(entity.getPrioridad());
        domain.setDescripcion(entity.getDescripcion());
        domain.setArea(entity.getArea());
        domain.setEstado(entity.getEstado());
        domain.setResponsableId(entity.getResponsableId());
        domain.setFechaCreacion(entity.getFechaCreacion());
        return domain;
    }

}