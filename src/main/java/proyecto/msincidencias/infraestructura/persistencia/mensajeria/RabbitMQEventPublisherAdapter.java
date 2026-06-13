package proyecto.msincidencias.infraestructura.persistencia.mensajeria;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import proyecto.msincidencias.aplication.ports.out.IncidenciaEventPublisherPort;
import proyecto.msincidencias.domion.model.Incidencias;
import proyecto.msincidencias.infraestructura.persistencia.mensajeria.dto.IncidenciaEventDTO;

@Component
@RequiredArgsConstructor
public class RabbitMQEventPublisherAdapter implements IncidenciaEventPublisherPort {


    private final RabbitTemplate rabbitTemplate;
    // Define el nombre del exchange (deberás configurarlo en RabbitMQ después)
    private static final String EXCHANGE_NAME = "incidencias.exchange";

    @Override
    public void publicarIncidenteCreado(Incidencias incidencias) {
        IncidenciaEventDTO dto = IncidenciaEventDTO.desdeDominio(incidencias);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"incident.created",dto);
        System.out.println("Evento publicado: incident.created con DTO para incidencia ID " + dto.id());
    }

    @Override
    public void publirIncidenteAsignado(Incidencias incidencias) {
        IncidenciaEventDTO dto = IncidenciaEventDTO.desdeDominio(incidencias);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "incident.assigned", dto); // [cite: 162]
        System.out.println("Evento publicado: incident.assigned con DTO para incidencia ID " + dto.id());
    }

    @Override
    public void publicarIncidenteActualizado(Incidencias incidencias) {
        IncidenciaEventDTO dto = IncidenciaEventDTO.desdeDominio(incidencias);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "incident.updated", dto); // [cite: 162]
        System.out.println("Evento publicado: incident.updated con DTO para incidencia ID " + dto.id());

    }

    @Override
    public void publicarIncidenteCerrado(Incidencias incidencias) {
        IncidenciaEventDTO dto = IncidenciaEventDTO.desdeDominio(incidencias);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "incident.closed", dto); // [cite: 163]
        System.out.println("Evento publicado: incident.closed con DTO para incidencia ID " + dto.id());

    }

}
