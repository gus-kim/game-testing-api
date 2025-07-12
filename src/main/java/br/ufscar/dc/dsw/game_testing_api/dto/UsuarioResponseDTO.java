package br.ufscar.dc.dsw.game_testing_api.dto;

import br.ufscar.dc.dsw.game_testing_api.model.Role;
import br.ufscar.dc.dsw.game_testing_api.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Role role
) {
    // Construtor que converte uma Entidade Usuario para este DTO
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
    }
}