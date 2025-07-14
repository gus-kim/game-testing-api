package br.ufscar.dc.dsw.game_testing_api.controller;

import br.ufscar.dc.dsw.game_testing_api.dto.ProjetoDTO;
import br.ufscar.dc.dsw.game_testing_api.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.game_testing_api.model.Usuario;
import br.ufscar.dc.dsw.game_testing_api.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criarProjeto(@RequestBody ProjetoDTO projetoDTO) {
        try {
            ProjetoDTO novoProjeto = projetoService.criarProjeto(projetoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> listarProjetos(@AuthenticationPrincipal Usuario usuario,
                                                             @RequestParam(defaultValue = "nome") String sortBy,
                                                             @RequestParam(defaultValue = "asc") String order) {
        List<ProjetoDTO> projetos = projetoService.listarProjetos(usuario, sortBy, order);
        return ResponseEntity.ok(projetos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoDTO projetoDTO) {
        try {
            ProjetoDTO projetoAtualizado = projetoService.atualizarProjeto(id, projetoDTO);
            return ResponseEntity.ok(projetoAtualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProjeto(@PathVariable Long id) {
        try {
            projetoService.deletarProjeto(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/membros")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> adicionarMembro(@PathVariable Long id, @RequestBody Long usuarioId) {
        try {
            projetoService.adicionarMembro(id, usuarioId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/membros/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removerMembro(@PathVariable Long id, @PathVariable Long usuarioId) {
        try {
            projetoService.removerMembro(id, usuarioId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
