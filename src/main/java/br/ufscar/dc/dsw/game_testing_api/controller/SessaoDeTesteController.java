package br.ufscar.dc.dsw.game_testing_api.controller;

import br.ufscar.dc.dsw.game_testing_api.dto.BugDTO;
import br.ufscar.dc.dsw.game_testing_api.dto.SessionResponseDTO;
import br.ufscar.dc.dsw.game_testing_api.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.game_testing_api.model.Bug;
import br.ufscar.dc.dsw.game_testing_api.model.Session;
import br.ufscar.dc.dsw.game_testing_api.service.BugService;
import br.ufscar.dc.dsw.game_testing_api.service.SessionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import br.ufscar.dc.dsw.game_testing_api.dto.BugResponseDTO;

@RestController
@RequestMapping("/api/projetos/{projetoId}/sessoes/{sessaoId}")
public class SessaoDeTesteController {

    private final SessionService sessionService;
    private final BugService bugService;

    public SessaoDeTesteController(SessionService sessionService, BugService bugService) {
        this.sessionService = sessionService;
        this.bugService = bugService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarSessao(@PathVariable Long sessaoId) {
        try {
            Session session = sessionService.updateSessionStatus(sessaoId, "IN_PROGRESS");
            return ResponseEntity.ok(session);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizarSessao(@PathVariable Long sessaoId) {
        try {
            Session session = sessionService.updateSessionStatus(sessaoId, "FINISHED");
            return ResponseEntity.ok(session);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/bugs")
    public ResponseEntity<?> registrarBug(@RequestBody BugDTO bugDTO) {
        try {
            Bug bug = bugService.registrarBug(bugDTO);
            return ResponseEntity.ok(bug);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> visualizarSessao(@PathVariable Long projetoId, @PathVariable Long sessaoId) {
        try {
            Session session = sessionService.getSessionById(sessaoId);

            // (Opcional) Verificar se a sessão pertence ao projeto informado
            if (!session.getProject().getId().equals(projetoId)) {
                return ResponseEntity.status(403).body("A sessão não pertence a este projeto.");
            }

            return ResponseEntity.ok(new SessionResponseDTO(session));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bugs")
    public ResponseEntity<?> listarBugs(@PathVariable Long sessaoId) {
        try {
            List<BugResponseDTO> bugs = bugService.listarBugsPorSessao(sessaoId);
            return ResponseEntity.ok(bugs);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/bugs")
    public ResponseEntity<?> deletarBugsDaSessao(@PathVariable Long sessaoId) {
        try {
            bugService.deletarTodosPorSessao(sessaoId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
