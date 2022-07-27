package co.com.todosistemas.prueba.service.impl;

import co.com.todosistemas.prueba.domain.Actividad;
import co.com.todosistemas.prueba.repository.ActividadRepository;
import co.com.todosistemas.prueba.repository.EmpleadoRepository;
import co.com.todosistemas.prueba.service.ActividadService;
import co.com.todosistemas.prueba.service.dto.ActividadDTO;
import co.com.todosistemas.prueba.service.mapper.ActividadMapper;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Actividad}.
 */
@Service
@Transactional
public class ActividadServiceImpl implements ActividadService {

    private final Logger log = LoggerFactory.getLogger(ActividadServiceImpl.class);

    private final ActividadRepository actividadRepository;

    private final ActividadMapper actividadMapper;

    public ActividadServiceImpl(ActividadRepository actividadRepository, ActividadMapper actividadMapper) {
        this.actividadRepository = actividadRepository;
        this.actividadMapper = actividadMapper;
    }

    @Override
    public ActividadDTO save(ActividadDTO actividadDTO) {
        log.debug("Request to save Actividad : {}", actividadDTO);
        Actividad actividad = actividadMapper.toEntity(actividadDTO);
        actividad = actividadRepository.save(actividad);
        return actividadMapper.toDto(actividad);
    }

    @Override
    public ActividadDTO update(ActividadDTO actividadDTO) {
        log.debug("Request to save Actividad : {}", actividadDTO);
        Actividad actividad = actividadMapper.toEntity(actividadDTO);
        actividad = actividadRepository.save(actividad);
        return actividadMapper.toDto(actividad);
    }

    @Override
    public Optional<ActividadDTO> partialUpdate(ActividadDTO actividadDTO) {
        log.debug("Request to partially update Actividad : {}", actividadDTO);

        return actividadRepository
            .findById(actividadDTO.getId())
            .map(existingActividad -> {
                actividadMapper.partialUpdate(existingActividad, actividadDTO);

                return existingActividad;
            })
            .map(actividadRepository::save)
            .map(actividadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> findAll() {
        log.debug("Request to get all Actividads");

        // SE RECORRE LA LISTA DE LAS ACTIVDAD Y CON EL METODO CREADO PARA CONSULTAR LOS DIAS, ENVIAMOS EL ID DE LA ACTIVIDAD
        return actividadRepository
            .findAll()
            .stream()
            .map(element -> {
                element.setDiasRetraso(diasRetraso(element.getId()));
                return element;
            })
            .collect(Collectors.toCollection(LinkedList::new));
    }

    // METODO PARA CONSULTAR LOS DIAS DE ATRASO QUE PUEDA TENER UNA ACTIVIDAD
    @Override
    public String diasRetraso(Long id) {
        log.debug("Request to find delay days");

        // SE CONSULTA LA ACTIVIDAD CON EL ID QUE SE ENVIA
        Optional<Actividad> actividad = actividadRepository.findById(id);

        String diasRetraso = "";



        // instanciamos la fecha actual
        Instant fechaActual = Instant.now();

        // SE TRAE LA FECHA  LIMITE DE LA ACTIVDAD GUARDADA
        Instant fechaLimite = actividad.get().getFechaEstimadaEjecucion();

        if (fechaLimite.isBefore(fechaActual)) {
            // CONVERTIMOS DE INSTANT A DATE LAS FECHAS PARA HCAER MAS SENCILLO EL MANEJO DE FECHAS. HORAS Y DIAS.
            Date fechaActualDate = Date.from(fechaActual);
            Date fechaLimiteDate = Date.from(fechaLimite);

            // UNA VEZ CASTEADAS LAS FECHAS, RECUPERAMOS LOS MILISEGUNDOS DE CADA UNA.
            Long milisegundosActual = fechaActualDate.getTime();
            Long milisegundosLimite = fechaLimiteDate.getTime();

            // SE HACE LA RESTA PARA OBTENER LA DIFERENCIA EN MILISEGUNDOS Y PROCEDER A CONVERTIRLA A DIAS
            Long diferencia = milisegundosActual - milisegundosLimite;

            // ALGORTIMO PARA CONVERTIR LA DIFERENCIA EN MILISEGUNDOS A DIAS
            double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
            diasRetraso = String.format("%.0f", dias);
        } else {
            diasRetraso = "0";
        }

        return diasRetraso;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Actividad> findOne(Long id) {
        log.debug("Request to get Actividad : {}", id);
        return actividadRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Actividad : {}", id);
        actividadRepository.deleteById(id);
    }
}
