package br.ufscar.dc.dsw.game_testing_api.controller;

import br.ufscar.dc.dsw.game_testing_api.dto.ProjetoDTO;
import br.ufscar.dc.dsw.game_testing_api.model.Usuario;
import br.ufscar.dc.dsw.game_testing_api.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ProjetoDTO> criarProjeto(@RequestBody ProjetoDTO projetoDTO) {
        ProjetoDTO novoProjeto = projetoService.criarProjeto(projetoDTO);
        return ResponseEntity.ok(novoProjeto);
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
    public ResponseEntity<ProjetoDTO> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoDTO projetoDTO) {
        ProjetoDTO projetoAtualizado = projetoService.atualizarProjeto(id, projetoDTO);
        return ResponseEntity.ok(projetoAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/membros")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> adicionarMembro(@PathVariable Long id, @RequestBody Long usuarioId) {
        projetoService.adicionarMembro(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/membros/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removerMembro(@PathVariable Long id, @PathVariable Long usuarioId) {
        projetoService.removerMembro(id, usuarioId);
        return ResponseEntity.ok().build();
    }
}
