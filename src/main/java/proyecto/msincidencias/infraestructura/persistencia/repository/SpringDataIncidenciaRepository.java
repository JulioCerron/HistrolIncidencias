package proyecto.msincidencias.infraestructura.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.msincidencias.infraestructura.persistencia.IncidenciaEntity;
@Repository
public interface SpringDataIncidenciaRepository extends JpaRepository<IncidenciaEntity , Long> {
}
