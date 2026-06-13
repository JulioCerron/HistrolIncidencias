package proyecto.msincidencias.infraestructura.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.msincidencias.domion.model.HistorialIncidencia;
import proyecto.msincidencias.infraestructura.persistencia.HistorialIncidenciaEntity;

import java.util.List;

public interface SpringDataHistorialRepository extends JpaRepository<HistorialIncidenciaEntity, Long> {
    List<HistorialIncidenciaEntity> findByIncidenciaIdOrderByFechaCambioDesc(Long incidenciaId);
}
