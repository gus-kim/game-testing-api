package br.ufscar.dc.dsw.game_testing_api.controller;

import br.ufscar.dc.dsw.game_testing_api.dto.SessionRequestDTO;
import br.ufscar.dc.dsw.game_testing_api.dto.SessionResponseDTO;
import br.ufscar.dc.dsw.game_testing_api.model.Projeto;
import br.ufscar.dc.dsw.game_testing_api.model.Session;
import br.ufscar.dc.dsw.game_testing_api.model.Estrategia;
import br.ufscar.dc.dsw.game_testing_api.model.SessionState;
import br.ufscar.dc.dsw.game_testing_api.repository.ProjetoRepository;
import br.ufscar.dc.dsw.game_testing_api.repository.SessionRepository;
import br.ufscar.dc.dsw.game_testing_api.repository.EstrategiaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionRepository sessionRepository;
    private final ProjetoRepository projetoRepository;
    private final EstrategiaRepository estrategiaRepository;

    public SessionController(SessionRepository sessionRepository, ProjetoRepository projetoRepository, EstrategiaRepository estrategiaRepository) {
        this.sessionRepository = sessionRepository;
        this.projetoRepository = projetoRepository;
        this.estrategiaRepository = estrategiaRepository;
    }

    @GetMapping
    public ResponseEntity<List<SessionResponseDTO>> getAllSessions() {
        List<SessionResponseDTO> sessions = sessionRepository.findAll().stream()
                .map(SessionResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable Long id) {
        return sessionRepository.findById(id)
                .map(session -> ResponseEntity.ok(new SessionResponseDTO(session)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SessionResponseDTO> createSession(@RequestBody SessionRequestDTO dto) {
        Projeto project = projetoRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project ID"));
        Estrategia strategy = estrategiaRepository.findById(dto.getStrategyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Strategy ID"));

        Session session = new Session();
        session.setProject(project);
        session.setStrategy(strategy);
        session.setTimeMinutes(dto.getTimeMinutes());
        session.setStatus(SessionState.CREATED);

        Session savedSession = sessionRepository.save(session);
        return new ResponseEntity<>(new SessionResponseDTO(savedSession), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> updateSession(@PathVariable Long id, @RequestBody SessionRequestDTO dto) {
        return sessionRepository.findById(id)
                .map(session -> {
                    Projeto project = projetoRepository.findById(dto.getProjectId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid Project ID"));
                    Estrategia strategy = estrategiaRepository.findById(dto.getStrategyId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid Strategy ID"));

                    session.setProject(project);
                    session.setStrategy(strategy);
                    session.setTimeMinutes(dto.getTimeMinutes());

                    Session updatedSession = sessionRepository.save(session);
                    return ResponseEntity.ok(new SessionResponseDTO(updatedSession));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        if (!sessionRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        sessionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateSessionStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        Optional<Session> optSession = sessionRepository.findById(id);
        if (optSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Session session = optSession.get();
        String newStatusStr = payload.get("status");
        if (newStatusStr == null) {
            return ResponseEntity.badRequest().body("Missing status");
        }

        SessionState newStatus;
        try {
            newStatus = SessionState.valueOf(newStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value");
        }

        if (newStatus == SessionState.IN_PROGRESS && session.getStartedAt() == null) {
            session.setStartedAt(LocalDateTime.now());
        } else if (newStatus == SessionState.FINISHED && session.getEndedAt() == null) {
            session.setEndedAt(LocalDateTime.now());
        }

        session.setStatus(newStatus);
        sessionRepository.save(session);

        return ResponseEntity.ok(new SessionResponseDTO(session));
    }
}