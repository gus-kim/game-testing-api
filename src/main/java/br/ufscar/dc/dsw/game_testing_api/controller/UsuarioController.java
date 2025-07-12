package br.ufscar.dc.dsw.game_testing_api.controller;

import br.ufscar.dc.dsw.game_testing_api.dto.UsuarioRequestDTO;
import br.ufscar.dc.dsw.game_testing_api.dto.UsuarioResponseDTO;
import br.ufscar.dc.dsw.game_testing_api.model.Usuario;
import br.ufscar.dc.dsw.game_testing_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para criar um novo usuário (POST /api/usuarios)
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO dto) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setSenha(dto.senha());
        novoUsuario.setRole(dto.role());

        Usuario usuarioSalvo = usuarioService.save(novoUsuario);
        URI location = URI.create("/api/usuarios/" + usuarioSalvo.getId());
        return ResponseEntity.created(location).body(new UsuarioResponseDTO(usuarioSalvo));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (usuarioService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}