package br.ufscar.dc.dsw.game_testing_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @GetMapping
    public ResponseEntity<String> listarUsuarios() {
        // Por enquanto, apenas retorna uma mensagem de sucesso para o teste.
        return ResponseEntity.ok("Acesso ao endpoint de usuários concedido!");
    }
}