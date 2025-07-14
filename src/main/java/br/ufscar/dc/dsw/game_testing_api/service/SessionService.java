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

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ProjetoRepository projetoRepository;
    private final EstrategiaRepository estrategiaRepository;

    public SessionService(SessionRepository sessionRepository, ProjetoRepository projetoRepository, EstrategiaRepository estrategiaRepository) {
        this.sessionRepository = sessionRepository;
        this.projetoRepository = projetoRepository;
        this.estrategiaRepository = estrategiaRepository;
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
            throw new EntityNotFoundException("Session not found");
        }
        sessionRepository.deleteById(id);
    }

    public Session updateSessionStatus(Long id, String newStatusStr) {
        Session session = getSessionById(id);

        SessionState newStatus;
        try {
            newStatus = SessionState.valueOf(newStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value");
        }

        if (newStatus == SessionState.IN_PROGRESS && session.getStartedAt() == null) {
            session.setStartedAt(LocalDateTime.now());
        } else if (newStatus == SessionState.FINISHED && session.getEndedAt() == null) {
            session.setEndedAt(LocalDateTime.now());
        }

        session.setStatus(newStatus);
        return sessionRepository.save(session);
    }
}
