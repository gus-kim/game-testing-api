package br.ufscar.dc.dsw.game_testing_api.dto;

import br.ufscar.dc.dsw.game_testing_api.model.Role;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha,
        Role role
) {}