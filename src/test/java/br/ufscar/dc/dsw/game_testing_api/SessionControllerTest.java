package br.ufscar.dc.dsw.game_testing_api;

import br.ufscar.dc.dsw.game_testing_api.controller.SessionController;
import br.ufscar.dc.dsw.game_testing_api.dto.SessionRequestDTO;
import br.ufscar.dc.dsw.game_testing_api.model.Estrategia;
import br.ufscar.dc.dsw.game_testing_api.model.Projeto;
import br.ufscar.dc.dsw.game_testing_api.repository.EstrategiaRepository;
import br.ufscar.dc.dsw.game_testing_api.repository.ProjetoRepository;
import br.ufscar.dc.dsw.game_testing_api.repository.SessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionRepository sessionRepository;

    @MockBean
    private ProjetoRepository projetoRepository;

    @MockBean
    private EstrategiaRepository estrategiaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateSession() throws Exception {
        // Mock data
        Projeto project = new Projeto();
        project.setId(1L);
        project.setNome("Test Project");

        Estrategia strategy = new Estrategia();
        strategy.setId(1L);
        strategy.setNome("Test Strategy");

        SessionRequestDTO sessionRequestDTO = new SessionRequestDTO();
        sessionRequestDTO.setProjectId(1L);
        sessionRequestDTO.setStrategyId(1L);
        sessionRequestDTO.setTimeMinutes(60);

        // Mock repository behavior
        // when(projetoRepository.findById(1L)).thenReturn(Optional.of(project));
        // when(estrategiaRepository.findById(1L)).thenReturn(Optional.of(strategy));

        // Perform POST request
        mockMvc.perform(post("/api/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionRequestDTO)))
                .andExpect(status().isCreated());
    }
}
