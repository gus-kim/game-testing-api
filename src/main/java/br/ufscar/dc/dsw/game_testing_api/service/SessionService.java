package br.ufscar.dc.dsw.game_testing_api.service;

import br.ufscar.dc.dsw.game_testing_api.dto.SessionRequestDTO;
import br.ufscar.dc.dsw.game_testing_api.model.Estrategia;
import br.ufscar.dc.dsw.game_testing_api.model.Projeto;
import br.ufscar.dc.dsw.game_testing_api.model.Session;
import br.ufscar.dc.dsw.game_testing_api.model.SessionState;
import br.ufscar.dc.dsw.game_testing_api.repository.EstrategiaRepository;
import br.ufscar.dc.dsw.game_testing_api.repository.ProjetoRepository;
import br.ufscar.dc.dsw.game_testing_api.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import br.ufscar.dc.dsw.game_testing_api.repository.BugRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ProjetoRepository projetoRepository;
    private final EstrategiaRepository estrategiaRepository;
    private final BugRepository bugRepository;

    public SessionService(SessionRepository sessionRepository, ProjetoRepository projetoRepository, EstrategiaRepository estrategiaRepository, BugRepository bugRepository) {
        this.sessionRepository = sessionRepository;
        this.projetoRepository = projetoRepository;
        this.estrategiaRepository = estrategiaRepository;
        this.bugRepository = bugRepository;
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Session not found"));
    }

    public Session createSession(SessionRequestDTO dto) {
        Projeto project = projetoRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid Project ID"));
        Estrategia strategy = estrategiaRepository.findById(dto.getStrategyId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid Strategy ID"));

        Session session = new Session();
        session.setProject(project);
        session.setStrategy(strategy);
        session.setTimeMinutes(dto.getTimeMinutes());
        session.setStatus(SessionState.CREATED);

        return sessionRepository.save(session);
    }

    public Session updateSession(Long id, SessionRequestDTO dto) {
        Session session = getSessionById(id);

        Projeto project = projetoRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid Project ID"));
        Estrategia strategy = estrategiaRepository.findById(dto.getStrategyId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid Strategy ID"));

        session.setProject(project);
        session.setStrategy(strategy);
        session.setTimeMinutes(dto.getTimeMinutes());

        return sessionRepository.save(session);
    }

    public void deleteSession(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new EntityNotFoundException("Sessão não encontrada");
        }
        Session session = getSessionById(id);

        // deleta bugs diretamente
        bugRepository.deleteAllBySession_Id(id);

        // depois deleta sessão
        sessionRepository.deleteById(id);
    }


    public Session updateSessionStatus(Long id, String newStatusStr) {
        Session session = getSessionById(id);
        SessionState newStatus = SessionState.valueOf(newStatusStr.toUpperCase());

        switch (newStatus) {
            case IN_PROGRESS:
                if (session.getStatus() != SessionState.CREATED) {
                    throw new IllegalStateException("A sessão só pode ser iniciada a partir do estado CRIADA.");
                }
                session.setStartedAt(LocalDateTime.now());
                break;
            case FINISHED:
                if (session.getStatus() != SessionState.IN_PROGRESS) {
                    throw new IllegalStateException("A sessão só pode ser finalizada se estiver em execução.");
                }
                session.setEndedAt(LocalDateTime.now());
                break;
            default:
                throw new IllegalArgumentException("Transição de estado inválida.");
        }

        session.setStatus(newStatus);
        return sessionRepository.save(session);
    }
}
