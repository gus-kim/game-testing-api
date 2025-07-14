package br.ufscar.dc.dsw.game_testing_api.controller;

import br.ufscar.dc.dsw.game_testing_api.dto.SessionRequestDTO;
import br.ufscar.dc.dsw.game_testing_api.dto.SessionResponseDTO;
import br.ufscar.dc.dsw.game_testing_api.model.Session;
import br.ufscar.dc.dsw.game_testing_api.service.SessionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<SessionResponseDTO>> getAllSessions() {
        List<SessionResponseDTO> sessions = sessionService.getAllSessions().stream()
                .map(SessionResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable Long id) {
        try {
            Session session = sessionService.getSessionById(id);
            return ResponseEntity.ok(new SessionResponseDTO(session));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SessionResponseDTO> createSession(@Valid @RequestBody SessionRequestDTO dto) {
        try {
            Session savedSession = sessionService.createSession(dto);
            return new ResponseEntity<>(new SessionResponseDTO(savedSession), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> updateSession(@PathVariable Long id, @RequestBody SessionRequestDTO dto) {
        try {
            Session updatedSession = sessionService.updateSession(id, dto);
            return ResponseEntity.ok(new SessionResponseDTO(updatedSession));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        try {
            sessionService.deleteSession(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateSessionStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String newStatusStr = payload.get("status");
        if (newStatusStr == null) {
            return ResponseEntity.badRequest().body("Missing status");
        }

        try {
            Session updatedSession = sessionService.updateSessionStatus(id, newStatusStr);
            return ResponseEntity.ok(new SessionResponseDTO(updatedSession));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}