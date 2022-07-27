package co.com.todosistemas.prueba.repository;

import co.com.todosistemas.prueba.domain.Empleado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Empleado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // TODO metodo para consultar los empleados que no estan tienen una actividad relacionada pendiente.

    // CONSULTA SQL BASICA PARA VALIDAR QUE EL ID DE EL EMPLEADO A CONSULTAR NO ESTE RELACIONADO EN UNA ACTIVDAD EN ESTADO PENDIENTE.
    @Query(value = "SELECT e FROM Empleado e WHERE e.id NOT IN (SELECT a.empleado.id FROM Actividad a WHERE a.estado = 'PENDIENTE')")
    List<Empleado> empleadosDisponibles();
}
