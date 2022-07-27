package co.com.todosistemas.prueba.web.rest;

import co.com.todosistemas.prueba.repository.EmpleadoRepository;
import co.com.todosistemas.prueba.service.EmpleadoService;
import co.com.todosistemas.prueba.service.dto.EmpleadoDTO;
import co.com.todosistemas.prueba.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.com.todosistemas.prueba.domain.Empleado}.
 */
@RestController
@RequestMapping("/api")
public class EmpleadoResource {

    private final Logger log = LoggerFactory.getLogger(EmpleadoResource.class);

    private static final String ENTITY_NAME = "empleado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpleadoService empleadoService;

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoResource(EmpleadoService empleadoService, EmpleadoRepository empleadoRepository) {
        this.empleadoService = empleadoService;
        this.empleadoRepository = empleadoRepository;
    }

    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoDTO> createEmpleado(@RequestBody EmpleadoDTO empleadoDTO) throws URISyntaxException {
        log.debug("REST request to save Empleado : {}", empleadoDTO);
        if (empleadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new empleado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpleadoDTO result = empleadoService.save(empleadoDTO);
        return ResponseEntity
            .created(new URI("/api/empleados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> updateEmpleado(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmpleadoDTO empleadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Empleado : {}, {}", id, empleadoDTO);
        if (empleadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empleadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empleadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmpleadoDTO result = empleadoService.update(empleadoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empleadoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/empleados-disponibles")
    public List<EmpleadoDTO> empleadosDisponibles() {
        log.debug("REST request to get all aviable employes");
        return empleadoService.empleadosDisponbiles();
    }


    @PatchMapping(value = "/empleados/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpleadoDTO> partialUpdateEmpleado(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmpleadoDTO empleadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Empleado partially : {}, {}", id, empleadoDTO);
        if (empleadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empleadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empleadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpleadoDTO> result = empleadoService.partialUpdate(empleadoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empleadoDTO.getId().toString())
        );
    }


    @GetMapping("/empleados")
    public List<EmpleadoDTO> getAllEmpleados() {
        log.debug("REST request to get all Empleados");
        return empleadoService.findAll();
    }


    @GetMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> getEmpleado(@PathVariable Long id) {
        log.debug("REST request to get Empleado : {}", id);
        Optional<EmpleadoDTO> empleadoDTO = empleadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empleadoDTO);
    }


    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        log.debug("REST request to delete Empleado : {}", id);
        empleadoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
