package co.com.todosistemas.prueba.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.todosistemas.prueba.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpleadoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpleadoDTO.class);
        EmpleadoDTO empleadoDTO1 = new EmpleadoDTO();
        empleadoDTO1.setId(1L);
        EmpleadoDTO empleadoDTO2 = new EmpleadoDTO();
        assertThat(empleadoDTO1).isNotEqualTo(empleadoDTO2);
        empleadoDTO2.setId(empleadoDTO1.getId());
        assertThat(empleadoDTO1).isEqualTo(empleadoDTO2);
        empleadoDTO2.setId(2L);
        assertThat(empleadoDTO1).isNotEqualTo(empleadoDTO2);
        empleadoDTO1.setId(null);
        assertThat(empleadoDTO1).isNotEqualTo(empleadoDTO2);
    }
}
