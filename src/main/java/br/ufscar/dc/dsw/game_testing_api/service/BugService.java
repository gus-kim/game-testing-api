package br.ufscar.dc.dsw.game_testing_api.service;

import br.ufscar.dc.dsw.game_testing_api.dto.BugDTO;
import br.ufscar.dc.dsw.game_testing_api.dto.BugResponseDTO;
import br.ufscar.dc.dsw.game_testing_api.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.game_testing_api.model.Bug;
import br.ufscar.dc.dsw.game_testing_api.model.Session;
import br.ufscar.dc.dsw.game_testing_api.model.SessionState;
import br.ufscar.dc.dsw.game_testing_api.repository.BugRepository;
import br.ufscar.dc.dsw.game_testing_api.repository.SessionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BugService {

    private final BugRepository bugRepository;
    private final SessionRepository sessionRepository;

    public BugService(BugRepository bugRepository, SessionRepository sessionRepository) {
        this.bugRepository = bugRepository;
        this.sessionRepository = sessionRepository;
    }

    public Bug registrarBug(BugDTO dto) {
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Sessão não encontrada"));

        if (session.getStatus() == SessionState.FINISHED || session.getStatus() == SessionState.CREATED || session.getStatus() == SessionState.CANCELED) {
            throw new IllegalStateException("Não é possível adicionar bugs em uma sessão que não esteja em progresse (IN_PROGRESS).");
        }

        Bug bug = new Bug();
        bug.setTitulo(dto.getTitulo());
        bug.setDescricao(dto.getDescricao());
        bug.setSession(session);

        session.getBugs().add(bug);

        return bugRepository.save(bug);
    }

    public List<BugResponseDTO> listarBugsPorSessao(Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new ResourceNotFoundException("Sessão não encontrada");
        }

        return bugRepository.findBySession_Id(sessionId)
                .stream()
                .map(bug -> new BugResponseDTO(bug.getId(), bug.getTitulo(), bug.getDescricao()))
                .toList();
    }

    public void deletarTodosPorSessao(Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new ResourceNotFoundException("Sessão não encontrada");
        }
        bugRepository.deleteAllBySession_Id(sessionId);
    }
}
