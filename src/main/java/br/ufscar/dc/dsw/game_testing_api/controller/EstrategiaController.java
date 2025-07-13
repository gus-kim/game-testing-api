package br.ufscar.dc.dsw.game_testing_api.controller;

import br.ufscar.dc.dsw.game_testing_api.dto.EstrategiaRequestDTO;
import br.ufscar.dc.dsw.game_testing_api.dto.EstrategiaResponseDTO;
import br.ufscar.dc.dsw.game_testing_api.service.EstrategiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estrategias")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstrategiaController {

    @Autowired
    private EstrategiaService estrategiaService;

    /**
     * Lista todas as estratégias (público)
     */
    @GetMapping
    public ResponseEntity<List<EstrategiaResponseDTO>> listarTodas() {
        try {
            List<EstrategiaResponseDTO> estrategias = estrategiaService.listarTodas();
            return ResponseEntity.ok(estrategias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lista estratégias com paginação (público)
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<EstrategiaResponseDTO>> listarComPaginacao(Pageable pageable) {
        try {
            Page<EstrategiaResponseDTO> estrategias = estrategiaService.listarComPaginacao(pageable);
            return ResponseEntity.ok(estrategias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Busca estratégia por ID (público)
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstrategiaResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            EstrategiaResponseDTO estrategia = estrategiaService.buscarPorId(id);
            return ResponseEntity.ok(estrategia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Busca estratégias por nome (público)
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<EstrategiaResponseDTO>> buscarPorNome(@RequestParam String nome) {
        try {
            List<EstrategiaResponseDTO> estrategias = estrategiaService.buscarPorNome(nome);
            return ResponseEntity.ok(estrategias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cria uma nova estratégia (apenas administradores)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criar(@RequestBody EstrategiaRequestDTO requestDTO) {
        try {
            // Validações básicas
            if (requestDTO.getNome() == null || requestDTO.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome da estratégia é obrigatório");
            }

            EstrategiaResponseDTO estrategiaCriada = estrategiaService.criar(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(estrategiaCriada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    /**
     * Atualiza uma estratégia existente (apenas administradores)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EstrategiaRequestDTO requestDTO) {
        try {
            // Validações básicas
            if (requestDTO.getNome() == null || requestDTO.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome da estratégia é obrigatório");
            }

            EstrategiaResponseDTO estrategiaAtualizada = estrategiaService.atualizar(id, requestDTO);
            return ResponseEntity.ok(estrategiaAtualizada);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    /**
     * Remove uma estratégia (apenas administradores)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            estrategiaService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
}
