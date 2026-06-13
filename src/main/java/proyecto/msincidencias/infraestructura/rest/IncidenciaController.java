package proyecto.msincidencias.infraestructura.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.msincidencias.aplication.ports.in.ConsultarHistorialUseCase;
import proyecto.msincidencias.aplication.ports.in.GestionarEstadoIncidenciaUseCase;
import proyecto.msincidencias.aplication.ports.in.RegistrarIncidenciaUseCase;
import proyecto.msincidencias.domion.model.Estado;
import proyecto.msincidencias.domion.model.HistorialIncidencia;
import proyecto.msincidencias.domion.model.Incidencias;
import proyecto.msincidencias.infraestructura.rest.dto.IncidenciaRequestDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/incidencias")
@Tag(name = "Incidencias", description = "API para la gestión de incidencias y su historial")
public class IncidenciaController {

    private final RegistrarIncidenciaUseCase registrarIncidenciaUseCase;
    private final GestionarEstadoIncidenciaUseCase gestionarEstadoIncidenciaUseCase;
    // 🟢 NUEVO: Inyectamos el caso de uso del historial
    private final ConsultarHistorialUseCase consultarHistorialUseCase;

    // 1. Registrar una nueva incidencia
    @PostMapping
    @Operation(summary = "Registrar incidencia", description = "Crea una incidencia inicializándola en estado REGISTRADA")
    public ResponseEntity<Incidencias> registrarIncidencia(@RequestBody IncidenciaRequestDTO request) {
        // Mapeamos el DTO de entrada a nuestro modelo de dominio
        Incidencias nuevaIncidencia = new Incidencias();
        nuevaIncidencia.setPrioridad(request.prioridad());
        nuevaIncidencia.setDescripcion(request.descripcion());
        nuevaIncidencia.setArea(request.area());

        Incidencias registrada = registrarIncidenciaUseCase.registrar(nuevaIncidencia);
        return new ResponseEntity<>(registrada, HttpStatus.CREATED);
    }

    // 2. Cambiar el estado de una incidencia
    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado", description = "Actualiza el estado y guarda el cambio automáticamente en el historial")
    public ResponseEntity<Incidencias> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Estado nuevoEstado) {

        Incidencias actualizada = gestionarEstadoIncidenciaUseCase.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.ok(actualizada);
    }

    // 3. Asignar un responsable
    @PutMapping("/{id}/responsable/{idResponsable}")
    @Operation(summary = "Asignar responsable", description = "Asigna un empleado y guarda la acción automáticamente en el historial")
    public ResponseEntity<Incidencias> asignarResponsable(
            @PathVariable Long id,
            @PathVariable Long idResponsable) {

        Incidencias actualizada = gestionarEstadoIncidenciaUseCase.asignarResponsable(id, idResponsable);
        return ResponseEntity.ok(actualizada);
    }


    @GetMapping("/{id}/historial")
    @Operation(summary = "Consultar historial", description = "Obtiene la línea de tiempo completa de los cambios de una incidencia")
    public ResponseEntity<List<HistorialIncidencia>> consultarHistorial(@PathVariable Long id) {
        List<HistorialIncidencia> historial = consultarHistorialUseCase.consultarPorIncidencia(id);
        return ResponseEntity.ok(historial);
    }
}