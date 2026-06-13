package proyecto.msincidencias.infraestructura.config;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import proyecto.msincidencias.aplication.ports.out.HistorialRepositoryPort;
import proyecto.msincidencias.aplication.ports.out.IncidenciaEventPublisherPort;
import proyecto.msincidencias.aplication.ports.out.IncidenciaRepositoryPort;
import proyecto.msincidencias.aplication.service.IncidenciasService;
import proyecto.msincidencias.infraestructura.persistencia.adaptador.HistorialPersistenceAdapter;
import proyecto.msincidencias.infraestructura.persistencia.adaptador.IncidenciaPersistenceAdapter;
import proyecto.msincidencias.infraestructura.persistencia.repository.SpringDataHistorialRepository;
import proyecto.msincidencias.infraestructura.persistencia.repository.SpringDataIncidenciaRepository;

@Configuration
public class IncidenciaConfig {

    //Registramos el servicio de dominio como bean
    @Bean
    public IncidenciasService incidenciaDomainService(
            IncidenciaRepositoryPort repositoryPort,
            IncidenciaEventPublisherPort eventPublisherPort,
            HistorialRepositoryPort historialRepository
    ) {
        return new IncidenciasService(repositoryPort, eventPublisherPort,historialRepository);
    }

    @Bean
    public IncidenciaPersistenceAdapter incidenciaPersistenceAdapter(
           SpringDataIncidenciaRepository repositoryPort
    ){
        return new IncidenciaPersistenceAdapter(repositoryPort);
    }

    @Bean
    public HistorialPersistenceAdapter historialPersistenciaAdapter(
            SpringDataHistorialRepository repository
    ){
        return new HistorialPersistenceAdapter(repository);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

}
